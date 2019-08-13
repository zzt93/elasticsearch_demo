package cn.superid.search.impl.entities.user.affair;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.filter;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.elasticsearch.search.aggregations.AggregationBuilders.topHits;

import cn.superid.common.rest.type.PublicType;
import cn.superid.common.rest.type.affair.AffairMoldType;
import cn.superid.search.entities.user.user.GuessQuery;
import cn.superid.search.impl.DefaultFetchSource;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.filter.InternalFilter;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms.Bucket;
import org.elasticsearch.search.aggregations.metrics.tophits.InternalTopHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AffairRecommendRepoImpl implements AffairRecommendCustom {

  private static final String MOLD = "mold";
  private static final PageRequest EMPTY = PageRequest.of(0, 1);
  private final ElasticsearchTemplate template;

  @Override
  public Page<AffairPO> recommend(GuessQuery query) {
    Preconditions.checkNotNull(query);
    PageRequest pageRequest = query.getPageRequest();
    Preconditions.checkNotNull(pageRequest);

    final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(
            QueryBuilders.boolQuery()
                .should(termQuery(MOLD, AffairMoldType.NORMAL.getMold()))
                .should(termQuery(MOLD, AffairMoldType.SPACE.getMold()))
                .should(termQuery(MOLD, AffairMoldType.PERSONAL.getMold()))
        )
        ;
    final FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
        .functionScoreQuery(boolQuery,
            ScoreFunctionBuilders.randomFunction(System.currentTimeMillis()))
        .boostMode(CombineFunction.REPLACE);

    String aggName = "has-mold";
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("affair-*")
        .withQuery(functionScoreQueryBuilder)
        .withPageable(EMPTY)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .addAggregation(filter(aggName, boolQuery)
            .subAggregation(terms("molds").field(MOLD)
                .subAggregation(topHits("top").size(pageRequest.getPageSize()))))
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
    return new AggregatedPageImpl<>(res, pageRequest, response.getHits().getTotalHits());
  }

}
