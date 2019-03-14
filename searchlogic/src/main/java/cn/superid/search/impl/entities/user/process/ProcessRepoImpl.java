package cn.superid.search.impl.entities.user.process;


import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import cn.superid.common.rest.constant.workflow.ApplySource;
import cn.superid.search.entities.user.process.ProcessQuery;
import cn.superid.search.entities.user.process.ProcessQuery.QueryType;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
        .withPageable(pageable)
        .build();
    return template
        .queryForPage(searchQuery, ProcessPO.class);
  }

  @Override
  public Map<Long, Long> count(ProcessQuery query) {
    BoolQueryBuilder bool = getQuery(query);
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(getIndices(query))
        .withTypes("messages")
        .addAggregation(terms("status").field("id"))
        .withQuery(bool).build();

    Aggregations aggregations = template.query(searchQuery, SearchResponse::getAggregations);
    StringTerms ids = (StringTerms) aggregations.asMap().get("status");
    Map<Long, Long> res = new HashMap<>();
    for (Bucket bucket : ids.getBuckets()) {
      res.put(bucket.getKeyAsNumber().longValue(), bucket.getDocCount());
    }
    return res;
  }

//  private BoolQueryBuilder getQuery(ProcessQuery query, Pageable pageable) {
//    Preconditions.checkArgument(pageable != null);
//    BoolQueryBuilder bool = boolQuery();
//    //keyword
//    if (query.getQuery() != null) {
//      bool = bool.must(wildcardQuery("name", wildcard(query.getQuery())).boost(10));
//    }
//    //time range
//    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//    bool.filter(rangeQuery("time")
//        .gt(dateFormat.format(new Date(query.getStartTime())))
//        .lt(dateFormat.format(new Date(query.getEndTime()))));
//    //affairId
//    List<Long> affairIds = query.getAffairIds();
//    if (query.getQueryType() == QueryType.TYPE_INNER) {
//      bool.filter(termsQuery("processBelongedAffairId", affairIds));
//    } else if (query.getQueryType() == QueryType.TYPE_OUTER) {
//      bool.mustNot(termsQuery("processBelongedAffairId", affairIds));
//    } else if (query.getQueryType() == QueryType.TYPE_CREATED){
//      bool.filter(termsQuery("affairId", affairIds));
//    }
//
//    //templateId
//    if (query.getTemplates() != null) {
//      bool.filter(termsQuery("templateId", query.getTemplates()));
//    }
//    //status
//    if (query.getStates() != null) {
//      bool.filter(termsQuery("status", query.getStates()));
//    }
//
//    //target
//    if (query.getTargetIds() != null){
//      bool.must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
//          .must(termQuery("sourceId", query.getTargetIds()));
//    }
//    //ann
//    if (query.getAnnIds() != null){
//      bool.must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
//          .must(termQuery("sourceId", query.getAnnIds()));
//    }
//
//    //admin
//    BoolQueryBuilder admin = boolQuery();
//    if (query.getQueryType() != QueryType.TYPE_CREATED){
//      if (query.getAdminServiceIds() != null){
//        admin.should(termQuery("serviceId", query.getAdminServiceIds()));
//      }
//      if (query.getAdminTargetIds() != null){
//        admin.should(boolQuery()
//            .must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
//            .must(termQuery("sourceId", query.getAdminTargetIds())));
//      }
//      if (query.getAdminAnnIds() != null){
//        admin.should(boolQuery()
//            .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
//            .must(termQuery("sourceId", query.getAdminAnnIds())));
//      }
//      bool.filter(admin);
//    }else {
//      //creator can get list of provided service
//      bool.must(termQuery("serviceId", query.getAdminServiceIds()));
//    }
//
//    //role
//    if (query.getQueryType() == QueryType.TYPE_INNER || query.getQueryType() == QueryType.TYPE_OUTER) {
//      //launcher is roleId
//      if (query.getRoleIds() != null) {
//        bool.filter(termsQuery("roleId", query.getRoleIds()));
//      }
//    } else if (query.getQueryType() == QueryType.TYPE_ACT) {
//      //actor in roles
//      if (query.getRoleIds() != null) {
//        bool.filter(termsQuery("roles", query.getRoleIds()));
//      }
//    }
//    return bool;
//  }

  private BoolQueryBuilder getQuery(ProcessQuery query){
    BoolQueryBuilder bool = boolQuery();
    QueryType queryType = query.getQueryType();
    Integer sourceType = query.getSourceType();
    List<Long> affairIds = query.getAffairIds();

    //common query
    //keyword
    if (query.getQuery() != null) {
      bool = bool.must(wildcardQuery("name", wildcard(query.getQuery())).boost(10));
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
    //status
    if (query.getStates() != null) {
      bool.filter(termsQuery("status", query.getStates()));
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
            inner.should(
                boolQuery()
                    .must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
                    .must(termQuery("sourceId", query.getAdminTargetIds())));
          }
          if (query.getAdminAnnIds() != null){
            inner.should(
                boolQuery()
                    .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
                    .must(termQuery("sourceId", query.getAdminAnnIds())));
          }
          bool.filter(inner);
          break;
        case TYPE_OUTER:
          //affair
          bool.mustNot(termsQuery("processBelongedAffairId", affairIds));
          BoolQueryBuilder outer = boolQuery();
          //launcher is roleId
          if (query.getRoleIds() != null) {
            outer.should(termsQuery("roleId", query.getRoleIds()));
          }
          if (query.getAdminTargetIds() != null) {
            outer.should(
                boolQuery()
                    .must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
                    .must(termQuery("sourceId", query.getAdminTargetIds())));
          }
          if (query.getAdminAnnIds() != null){
            outer.should(
                boolQuery()
                    .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
                    .must(termQuery("sourceId", query.getAdminAnnIds())));
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
            act.should(
                boolQuery()
                    .must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
                    .must(termQuery("sourceId", query.getAdminTargetIds())));
          }
          if (query.getAdminAnnIds() != null){
            act.should(
                boolQuery()
                    .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
                    .must(termQuery("sourceId", query.getAdminAnnIds())));
          }
          bool.filter(act);
          break;
        case TYPE_CREATED:
          //setting in affair
          bool.filter(termsQuery("affairId", affairIds));
          //creator can get list of provided service
          if (query.getAdminServiceIds() != null) {
            bool.must(termQuery("serviceId", query.getAdminServiceIds()));
          }
          break;
      }
    } else if (sourceType == ApplySource.AIM.ordinal()){
      //target search
      //affair
      bool.filter(termsQuery("processBelongedAffairId", affairIds));
      //position
      BoolQueryBuilder position = boolQuery().should((
          boolQuery()
              .must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())))
              .must(getTq("sourceId", query.getTargetIds()))));
      position.should((
          boolQuery()
              .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
              .must(getTq("sourceId", query.getAnnIds()))));
      bool.filter(position);
      //normal role
      BoolQueryBuilder normal = boolQuery().must(boolQuery().should(getTq("roleId", query.getRoleIds())).should(getTq("roles", query.getRoleIds())));
      //service admin
      BoolQueryBuilder admin = boolQuery().must(termQuery("sourceType", String.valueOf(ApplySource.AIM.ordinal())));
      bool.filter(
          boolQuery()
              .should(normal)
              .should(admin));
    }else if (sourceType == ApplySource.ANN.ordinal()){
      //ann search
      //affair
      bool.filter(termsQuery("processBelongedAffairId", affairIds));
      BoolQueryBuilder position = boolQuery().should((
          boolQuery()
              .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
              .must(getTq("sourceId", query.getAnnIds()))));

      bool.filter(position);

      BoolQueryBuilder normal =
          boolQuery()
              .filter(getTq("roleId", query.getRoleIds()));
      //admin ann
      BoolQueryBuilder admin = boolQuery().should(
          boolQuery()
              .must(termQuery("sourceType", String.valueOf(ApplySource.ANN.ordinal())))
              .must(getTq("sourceId", query.getAdminAnnIds())));
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
}
