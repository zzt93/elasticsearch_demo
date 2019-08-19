package cn.superid.search.impl.entities.user.user;

import static org.elasticsearch.index.query.QueryBuilders.moreLikeThisQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import cn.superid.common.rest.type.PublicType;
import cn.superid.search.entities.user.user.InterestQuery;
import cn.superid.search.entities.user.user.StudentQuery;
import cn.superid.search.impl.DefaultFetchSource;
import com.google.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
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
  private static final Item[] EMPTY = new Item[]{};
  private static final String[] unlike = new String[]{"大学", "中学", "小学"};

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
        .must(moreLikeThisQuery(new String[]{"tags"}, new String[]{}, likeItems).minDocFreq(1).minTermFreq(1));

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

    QueryBuilder bool = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(termQuery("userId", query.getUserId()));
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices("personal_info")
        .withQuery(bool)
        .withSourceFilter(DefaultFetchSource.fields("_id", "type", "content", "description"))
        .build();
    List<PersonalInfo> infos = template.queryForList(searchQuery, PersonalInfo.class);
    List<Short> types = new ArrayList<>();
    StringBuilder likeTexts = new StringBuilder();
    for (PersonalInfo info : infos) {
      types.add(info.getType());
      likeTexts.append(info.getContent()).append(" ").append(info.getDescription());
    }

    QueryBuilder like = QueryBuilders.boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(termsQuery("type", types))
        .must(moreLikeThisQuery(new String[]{"content", "description"}, new String[]{likeTexts.toString()}, EMPTY)
            .unlike(unlike).minDocFreq(1).minTermFreq(1));
    SearchQuery moreLike = new NativeSearchQueryBuilder()
        .withIndices("personal_info")
        .withQuery(like)
        .withPageable(query.getPageRequest())
        .withSourceFilter(DefaultFetchSource.fields("_id", "affairId", "type", "content", "description"))
        .build();
    return template.queryForPage(moreLike, UserPO.class);
  }
}
