package cn.superid.search.impl.entities.user.user;

import static org.elasticsearch.index.query.QueryBuilders.moreLikeThisQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import cn.superid.common.rest.type.PublicType;
import cn.superid.search.entities.user.user.InterestQuery;
import cn.superid.search.entities.user.user.StudentQuery;
import cn.superid.search.impl.DefaultFetchSource;
import com.google.common.base.Preconditions;
import org.elasticsearch.index.query.MoreLikeThisQueryBuilder.Item;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
public class PersonalRepoImpl implements PersonalRecommendCustom {

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
        .must(moreLikeThisQuery(new String[]{"tags"}, new String[]{}, likeItems));

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
    Preconditions.checkNotNull(query.getPageRequest());

    long userId = query.getUserId();

    Item[] likeItems = new Item[]{new Item("user", "user", "" + userId)};
    QueryBuilder like = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(moreLikeThisQuery(new String[]{"unionId"}, new String[]{}, likeItems));

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("user")
        .withQuery(like)
        .withPageable(query.getPageRequest())
        .withSourceFilter(DefaultFetchSource.fields("_id", "personalAffairId", "unionId"))
        .build();
    return template.queryForPage(searchQuery, UserPO.class);
  }
}
