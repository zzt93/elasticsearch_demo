package cn.superid.search.impl.entities.user.affair;

import static cn.superid.search.impl.entities.user.affair.AffairPO.MOLD;
import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.filter;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.elasticsearch.search.aggregations.AggregationBuilders.topHits;

import cn.superid.common.rest.type.PublicType;
import cn.superid.search.entities.user.affair.AffairQuery;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.tophits.InternalTopHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * @author zzt
 */
public class AffairRepoImpl implements AffairCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<AffairPO> findAny(String info, Byte mold, List<Byte> notMold, Pageable pageable) {
    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("state", 0))
        .must(termQuery("publicType", PublicType.ALL))
        .must(
            boolQuery()
                .should(termQuery("superId", info))
                .should(wildcardQuery("name", wildcard(info)))
                .should(termQuery("tags", info))
        );
    if (mold != null) {
      bool.must(termQuery("mold", mold));
    }
    if (notMold != null) {
      bool.mustNot(termsQuery("mold", notMold));
    }
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(AffairPO.class))
        .withQuery(bool)
        .withPageable(pageable)
        .build();
    return template.queryForPage(searchQuery, AffairPO.class);
  }

  @Override
  public Page<AffairPO> findAny(AffairQuery affairQuery) {
    Preconditions.checkArgument(affairQuery.getMolds() != null, "Invalid mold");
    PageRequest pageable = affairQuery.getPageRequest();
    String info = affairQuery.getQuery();

    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("state", 0))
        .must(termQuery("publicType", PublicType.ALL))
        .must(termsQuery("mold", affairQuery.getMolds()))
        .must(
            boolQuery()
                .should(termQuery("superId", info))
                .should(wildcardQuery("name", wildcard(info)))
                .should(termQuery("tags", info))
        );

    return getAffairByMold(template, pageable, bool, bool);
  }

  static Page<AffairPO> getAffairByMold(ElasticsearchTemplate template, PageRequest pageable,
      QueryBuilder query, QueryBuilder aggFilter) {
    String aggName = "has-mold";
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(AffairPO.class))
        .withQuery(query)
        .withPageable(QueryHelper.EMPTY)
        .addAggregation(filter(aggName, aggFilter)
            .subAggregation(terms("molds").field(MOLD)
                .subAggregation(topHits("top").from((int) pageable.getOffset()).size(pageable.getPageSize()))))
        .build();
    SearchResponse response = template.query(searchQuery, t->t);
    Aggregations aggregations = response.getAggregations();
    LongTerms ids = ((InternalFilter)aggregations.get(aggName)).getAggregations().get("molds");
    List<AffairPO> res = new ArrayList<>();
    for (Bucket bucket : ids.getBuckets()) {
      for (SearchHit hit : ((InternalTopHits) bucket.getAggregations().get("top")).getHits()) {
        res.add(new AffairPO(hit.getId(), (Long) bucket.getKey()));
      }
    }
    return new AggregatedPageImpl<>(res, pageable, response.getHits().getTotalHits());
  }

  @Override
  public List<AffairPO> findAlliance(String info, Long allianceId, Pageable pageable) {
    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("state", 0))
//        .must(termQuery("publicType", PublicType.ALL))
        .must(termQuery("parentId", 0))
        .must(
            boolQuery()
                .should(wildcardQuery("name", wildcard(info)))
        );
    if (allianceId != null) {
      bool.mustNot(termQuery("allianceId", allianceId));
    }
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(AffairPO.class))
        .withQuery(bool)
        .withPageable(pageable)
        .build();
    return template.queryForPage(searchQuery, AffairPO.class).getContent();
  }

  @Override
  public Page<AffairPO> findByNameAndAllianceId(String name, Long allianceId, Pageable pageable) {
    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("state", 0))
//        .must(termQuery("publicType", PublicType.ALL))
        .must(termQuery("allianceId", allianceId))
        .must(
            boolQuery()
                .should(wildcardQuery("name", wildcard(name)))
        );
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexName(AffairPO.class, allianceId / AffairPO.CLUSTER_SIZE))
        .withQuery(bool)
        .withPageable(pageable)
        .build();
    return template.queryForPage(searchQuery, AffairPO.class);
  }
}
