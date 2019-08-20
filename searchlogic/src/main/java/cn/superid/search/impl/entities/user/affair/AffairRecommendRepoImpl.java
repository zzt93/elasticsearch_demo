package cn.superid.search.impl.entities.user.affair;

import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import cn.superid.common.rest.type.PublicType;
import cn.superid.common.rest.type.affair.AffairMoldType;
import cn.superid.search.entities.user.user.GuessQuery;
import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AffairRecommendRepoImpl implements AffairRecommendCustom {

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
                .should(termQuery(AffairPO.MOLD, AffairMoldType.NORMAL.getMold()))
                .should(termQuery(AffairPO.MOLD, AffairMoldType.SPACE.getMold()))
                .should(termQuery(AffairPO.MOLD, AffairMoldType.PERSONAL.getMold()))
        );
    final FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
        .functionScoreQuery(boolQuery,
            ScoreFunctionBuilders.randomFunction(System.currentTimeMillis()))
        .boostMode(CombineFunction.REPLACE);

    return AffairRepoImpl.getAffairByMold(template, pageRequest, functionScoreQueryBuilder, boolQuery);
  }

}
