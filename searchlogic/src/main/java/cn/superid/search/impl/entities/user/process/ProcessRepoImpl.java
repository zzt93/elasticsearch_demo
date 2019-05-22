package cn.superid.search.impl.entities.user.process;


import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import cn.superid.common.rest.constant.workflow.ApplySource;
import cn.superid.search.entities.user.process.ProcessCountVO;
import cn.superid.search.entities.user.process.ProcessQuery;
import cn.superid.search.entities.user.process.ProcessQuery.QueryType;
import cn.superid.search.impl.DefaultFetchSource;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author ypc
 */
@Component
public class ProcessRepoImpl implements ProcessCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<ProcessPO> find(ProcessQuery query, Pageable pageable) {
    Preconditions.checkArgument(pageable != null);
    // TODO: 2019-03-14 checkArgument sourceType in affair target ann
    // TODO: 2019-03-14 checkArgument queryType in inner outer actor create
    BoolQueryBuilder bool = getQuery(query);

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(getIndices(query))
        .withQuery(bool)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .withPageable(pageable)
        .build();
    return template
        .queryForPage(searchQuery, ProcessPO.class);
  }

  @Override
  public ProcessCountVO count(ProcessQuery query) {
    BoolQueryBuilder bool = getQuery(query);
    NativeSearchQuery countQuery = new NativeSearchQueryBuilder()
        .withIndices(getIndices(query))
        .withTypes("process")
        .addAggregation(terms("ids").field("status"))
        .withQuery(bool).build();

    Aggregations aggregations = template.query(countQuery, SearchResponse::getAggregations);
    LongTerms ids = (LongTerms) aggregations.asMap().get("ids");
    Map<Long, Long> res = new HashMap<>();
    for (Bucket bucket : ids.getBuckets()) {
      res.put(bucket.getKeyAsNumber().longValue(), bucket.getDocCount());
    }

    bool.filter(termQuery("status", 0L));
    List<Long> list;
    int size = res.getOrDefault(0L, 0L).intValue();
    if (size == 0){
      list = new ArrayList<>();
    }else {
      SearchQuery searchQuery = new NativeSearchQueryBuilder()
          .withIndices(getIndices(query))
          .withQuery(bool)
          .withSourceFilter(DefaultFetchSource.defaultId())
          .withPageable(PageRequest.of(0, size))
          .build();
      list = template.queryForIds(searchQuery).stream().map(Long::valueOf).collect(Collectors.toList());
    }


    ProcessCountVO vo = new ProcessCountVO();
    vo.setCountMap(res);
    vo.setOngingList(list);
    return vo;
  }

  private BoolQueryBuilder getQuery(ProcessQuery query){
    BoolQueryBuilder bool = boolQuery();
    QueryType queryType = query.getQueryType();
    Integer sourceType = query.getSourceType() == null ? ApplySource.AFFAIR.ordinal() : query.getSourceType();
    List<Long> affairIds = query.getAffairIds();

    //common query
    //keyword
    if (query.getQuery() != null) {
      bool = bool.must(wildcardQuery("name", wildcard(query.getQuery())).boost(10));
    }

    if (query.getSerial() != null) {
      bool = bool.must(wildcardQuery("serial", wildcard(query.getSerial())).boost(100));
    }
    //time range
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    bool.filter(
        rangeQuery("time")
            .gt(dateFormat.format(new Date(query.getStartTime())))
            .lt(dateFormat.format(new Date(query.getEndTime()))));
    //templateId
    if (query.getTemplates() != null) {
      bool.filter(termsQuery("templateId", query.getTemplates()));
    }
    //serviceId
    if (query.getServiceIds() != null) {
      bool.filter(termsQuery("serviceId", query.getServiceIds()));
    }
    BoolQueryBuilder stateOrIds = boolQuery();
    //status or pids
    if (query.getStates() != null) {
      stateOrIds.should(termsQuery("status", query.getStates()));
    }
    if (query.getProcessIds() != null){
      stateOrIds.should(termsQuery("_id", query.getProcessIds()));
    }

    bool.filter(stateOrIds);

    //filter by initiator
    if (query.getStarterRoleIds() !=null) {
      bool.filter(boolQuery().should(termsQuery("roleId", query.getStarterRoleIds())));
    }
    if (sourceType == ApplySource.AFFAIR.ordinal()){
      //affair search
      switch (queryType) {
        case TYPE_INNER:
          //affair
          bool.filter(termsQuery("processBelongedAffairId", affairIds));
          BoolQueryBuilder inner = boolQuery();
          //launcher is roleId
          if (query.getRoleIds() != null) {
            inner.should(termsQuery("roleId", query.getRoleIds()));
          }
          if (query.getAdminTargetIds() != null) {
            inner.should(getTargetSourceTq(query.getAdminTargetIds()));
          }
          if (query.getAdminAnnIds() != null){
            inner.should(getAnnSourceTq(query.getAdminAnnIds()));
          }
          bool.filter(inner);
          break;
        case TYPE_OUTER:
          //affair
          bool.mustNot(termsQuery("processBelongedAffairId", affairIds));
          bool.must(termsQuery("roleBelongedAffairId", affairIds));
          BoolQueryBuilder outer = boolQuery();
          //launcher is roleId
          if (query.getRoleIds() != null) {
            outer.should(termsQuery("roleId", query.getRoleIds()));
          }
          if (query.getAdminTargetIds() != null) {
            outer.should(getTargetSourceTq(query.getAdminTargetIds()));
          }
          if (query.getAdminAnnIds() != null){
            outer.should(getAnnSourceTq(query.getAdminAnnIds()));
          }
          bool.filter(outer);
          break;
        case TYPE_ACT:
          //no affair condition
          BoolQueryBuilder act = boolQuery();
          //actor in roles
          if (query.getRoleIds() != null) {
            act.should(termsQuery("roles", query.getRoleIds()));
          }
          if (query.getAdminTargetIds() != null) {
            act.should(getTargetSourceTq(query.getAdminTargetIds()));
          }
          if (query.getAdminAnnIds() != null){
            act.should(getAnnSourceTq(query.getAdminAnnIds()));
          }
          bool.filter(act);
          break;
        case TYPE_CREATED:
          //setting in affair
          bool.filter(termsQuery("affairId", affairIds));
          //creator can get list of provided service
          if (query.getAdminServiceIds() != null) {
            bool.filter(termsQuery("serviceId", query.getAdminServiceIds()));
          }
          break;
      }
    } else if (sourceType == ApplySource.AIM.ordinal()){
      //target search
      //affair
      bool.filter(termsQuery("processBelongedAffairId", affairIds));
      //position
      BoolQueryBuilder position = boolQuery().should(getTargetSourceTq(query.getTargetIds()));
      position.should(getAnnSourceTq(query.getAnnIds()));
      bool.filter(position);
      //normal role
      BoolQueryBuilder normal = boolQuery();
      if (query.getRoleIds() !=null){
        normal.filter(boolQuery().should(termsQuery("roleId", query.getRoleIds())).should(termsQuery("roles", query.getRoleIds())));
      }
      //service admin
      BoolQueryBuilder admin = boolQuery().filter(getTq("serviceId", query.getAdminServiceIds()));
      bool.filter(
          boolQuery()
              .should(normal)
              .should(admin));
    }else if (sourceType == ApplySource.ANN.ordinal()){
      //ann search
      //affair
      bool.filter(termsQuery("processBelongedAffairId", affairIds));
      BoolQueryBuilder position = boolQuery().should(getAnnSourceTq(query.getAnnIds()));

      bool.filter(position);

      BoolQueryBuilder normal = boolQuery();
      if (query.getRoleIds() !=null){
        normal.filter(boolQuery().should(termsQuery("roleId", query.getRoleIds())).should(termsQuery("roles", query.getRoleIds())));
      }
      //admin ann
      BoolQueryBuilder admin = boolQuery().should(getAnnSourceTq(query.getAdminAnnIds()));
      bool.filter(
          boolQuery()
              .should(normal)
              .should(admin));

    }

    return bool;
  }

  private static String[] getIndices(ProcessQuery query){
    return new String[]{Suffix.indexNamePattern(ProcessPO.class)};
  }

  private static TermsQueryBuilder getTq(String field, List<Long> list){
    if (list == null){
      list = new ArrayList<>();
    }
    return termsQuery(field,list);
  }

  private static BoolQueryBuilder getAnnSourceTq(List<Long> ids){
    return boolQuery()
        .filter(boolQuery()
            .should(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
            .should(termQuery("sourceType", String.valueOf(ApplySource.FOOD.ordinal()))))
        .filter(getTq("sourceId", ids));
  }

  private static BoolQueryBuilder getTargetSourceTq(List<Long> ids){
    return boolQuery()
        .filter(boolQuery()
            .should(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
            .should(termQuery("sourceType", String.valueOf(ApplySource.MENKOR.ordinal()))))
        .filter(getTq("sourceId", ids));
  }
}
