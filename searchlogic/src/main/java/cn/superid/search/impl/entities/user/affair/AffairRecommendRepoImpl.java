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
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
public class AffairRecommendRepoImpl implements AffairRecommendCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<AffairPO> recommend(GuessQuery query) {
    Preconditions.checkNotNull(query);
    PageRequest pageRequest = query.getPageRequest();
    Preconditions.checkNotNull(pageRequest);

    final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .should(termQuery("mold", AffairMoldType.NORMAL.getMold()))
        .should(termQuery("mold", AffairMoldType.SPACE.getMold()))
        .should(termQuery("mold", AffairMoldType.PERSONAL.getMold()))
        ;
    final FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
        .functionScoreQuery(boolQuery,
            ScoreFunctionBuilders.randomFunction(System.currentTimeMillis()))
        .boostMode(CombineFunction.REPLACE);

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("affair-*")
        .withQuery(functionScoreQueryBuilder)
        .withSourceFilter(DefaultFetchSource.fields("_id", "mold"))
        .addAggregation(filter("has-mold", boolQuery)
            .subAggregation(terms("molds").field("mold")
                .subAggregation(topHits("top").size(pageRequest.getPageSize()))))
        .build();
    return template.queryForPage(searchQuery, AffairPO.class);
  }

}
