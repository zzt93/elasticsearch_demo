package cn.superid.search.impl.entities.user.user;

import static org.elasticsearch.index.query.QueryBuilders.existsQuery;
import static org.elasticsearch.index.query.QueryBuilders.idsQuery;
import static org.elasticsearch.index.query.QueryBuilders.moreLikeThisQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;
import static org.elasticsearch.search.aggregations.AggregationBuilders.topHits;

import cn.superid.common.rest.constant.affair.AuthStatus;
import cn.superid.common.rest.type.PublicType;
import cn.superid.search.entities.user.user.GuessQuery;
import cn.superid.search.entities.user.user.InterestQuery;
import cn.superid.search.entities.user.user.StudentQuery;
import cn.superid.search.impl.DefaultFetchSource;
import cn.superid.search.impl.query.QueryHelper;
import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.util.ArrayList;
import java.util.List;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.CombineFunction;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.Aggregations;
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
public class PersonalRepoImpl implements PersonalRecommendCustom {

  private static final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss.S").create();
  private static final int _100ms = 100;
  private final ElasticsearchTemplate template;

  @Autowired
  public PersonalRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }


  @Override
  public Page<UserPO> similarTag(InterestQuery query) {
    Preconditions.checkNotNull(query);
    Preconditions.checkNotNull(query.getPageRequest());

    long userId = query.getUserId();

    Item[] likeItems = new Item[]{new Item("user", "user", "" + userId)};
    QueryBuilder like = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(existsQuery("tags"))
        .mustNot(termQuery("tags", ""))
        .must(moreLikeThisQuery(new String[]{"tags"}, new String[]{}, likeItems).minDocFreq(1)
            .minTermFreq(1));

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("user")
        .withQuery(like)
        .withPageable(query.getPageRequest())
        .withSourceFilter(DefaultFetchSource.fields("_id", "personalAffairId", "tags"))
        .build();
    return template.queryForPage(searchQuery, UserPO.class);
  }

  @Override
  public Page<UserPO> similarStudent(StudentQuery query) {
    Preconditions.checkNotNull(query);
    PageRequest pageable = query.getPageRequest();
    Preconditions.checkNotNull(pageable);

    QueryBuilder bool = QueryBuilders.boolQuery()
        .must(termQuery("userId", query.getUserId()));
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("personal_info")
        .withQuery(bool)
        .withSourceFilter(DefaultFetchSource.fields("_id", "type", "content", "description"))
        .build();
    List<PersonalInfo> infos = template.queryForList(searchQuery, PersonalInfo.class);
    int size = infos.size();
    List<Short> types = new ArrayList<>(size);
    String[] ids = new String[size];
    List<String> contents = new ArrayList<>(size);
    List<String> likeTexts = new ArrayList<>(size);
    for (int i = 0; i < infos.size(); i++) {
      PersonalInfo info = infos.get(i);
      ids[i] = info.getId();
      types.add(info.getType());
      contents.add(info.getContent());
      likeTexts.add(info.getDescription());
    }

    QueryBuilder like = QueryBuilders.boolQuery()
        .mustNot(idsQuery("personal_info").addIds(ids))
        .must(termQuery("publicType", PublicType.ALL))
//        .must(termsQuery("type", types))
        .must(termsQuery("content", contents))
        .should(termsQuery("description", likeTexts));
    SearchQuery moreLike = new NativeSearchQueryBuilder()
        .withIndices("personal_info")
        .withQuery(like)
        .withPageable(QueryHelper.EMPTY)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .addAggregation(terms("uniq_affairId").field("affairId")
            .size((int) (pageable.getOffset() + pageable.getPageSize()))
            .subAggregation(topHits("top").from(0).size(1)))
        .build();
    SearchResponse response = template.query(moreLike, t -> t);
    Aggregations aggregations = response.getAggregations();
    LongTerms uniqAffairId = aggregations.get("uniq_affairId");
    List<UserPO> res = new ArrayList<>();
    for (int i = (int) pageable.getOffset(); i < uniqAffairId.getBuckets().size(); i++) {
      Bucket bucket = uniqAffairId.getBuckets().get(i);
      for (SearchHit hit : ((InternalTopHits) bucket.getAggregations().get("top")).getHits()) {
        PersonalInfo personalInfo = gson.fromJson(hit.getSourceAsString(), PersonalInfo.class);
        res.add(new UserPO(personalInfo.getAffairId(), personalInfo));
      }
    }
    return new AggregatedPageImpl<>(res, pageable, response.getHits().getTotalHits());
  }

  @Override
  public Page<UserPO> random(GuessQuery query) {
    Preconditions.checkNotNull(query);
    if (query.getScrollId() != null) {
      return template.continueScroll(query.getScrollId(), _100ms, UserPO.class);
    }
    PageRequest pageRequest = query.getPageRequest();
    Preconditions.checkNotNull(pageRequest);

    final BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(termQuery("authStatus", AuthStatus.REVIEW_PASS.getType()));
    final FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders
        .functionScoreQuery(boolQuery,
            ScoreFunctionBuilders.randomFunction(System.currentTimeMillis()))
        .boostMode(CombineFunction.REPLACE);

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("user")
        .withQuery(functionScoreQueryBuilder)
        .withPageable(pageRequest)
        .build();
    return template.startScroll(_100ms, searchQuery, UserPO.class);
  }

}
