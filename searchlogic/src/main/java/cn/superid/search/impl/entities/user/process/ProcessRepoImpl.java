package cn.superid.search.impl.entities.user.process;


import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.user.process.ProcessQuery;
import cn.superid.search.entities.user.process.ProcessQuery.QueryType;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
public class ProcessRepoImpl implements ProcessCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<ProcessPO> find(ProcessQuery query, Pageable pageable) {
    Preconditions.checkArgument(pageable != null);
    BoolQueryBuilder bool = boolQuery();
    //keyword
    if (query.getQuery() != null) {
      BoolQueryBuilder should = boolQuery()
          .should(wildcardQuery("name", wildcard(query.getQuery())).boost(10))
          .should(termQuery("tag", query.getQuery()).boost(5));
      bool = bool.must(should);
    }
    //time range
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    bool.filter(rangeQuery("time")
        .gt(dateFormat.format(new Date(query.getStartTime())))
        .lt(dateFormat.format(new Date(query.getEndTime()))));
    //affairId
    List<Long> affairIds = query.getAffairIds();
    String[] indexName = Suffix
        .indexName(ProcessPO.class, affairIds, id -> id / ProcessPO.CLUSTER_SIZE);
    if (query.getQueryType() == QueryType.TYPE_INNER) {
      bool.filter(termsQuery("roleBelongedAffairId", affairIds));
    } else if (query.getQueryType() == QueryType.TYPE_OUTER) {
      bool.mustNot(termsQuery("roleBelongedAffairId", affairIds));
      indexName = new String[]{Suffix.indexNamePattern(ProcessPO.class)};
    } else {
      bool.filter(termsQuery("affairId", affairIds));
    }
    //role
    if (query.getRoleIds() != null) {
      bool.filter(termsQuery("roles", query.getRoleIds()));
    }
    //templateId
    if (query.getTemplates() != null) {
      bool.filter(termsQuery("templateId", query.getTemplates()));
    }
    //status
    if (query.getStates() != null) {
      bool.filter(termsQuery("status", query.getStates()));
    }
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(indexName)
        .withQuery(bool)
        .withPageable(pageable)
        .build();
    return template
        .queryForPage(searchQuery, ProcessPO.class);
  }
}
