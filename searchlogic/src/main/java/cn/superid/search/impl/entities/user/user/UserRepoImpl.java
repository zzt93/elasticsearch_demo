package cn.superid.search.impl.entities.user.user;

import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.common.rest.type.PublicType;
import cn.superid.search.impl.DefaultFetchSource;
import com.google.common.base.Preconditions;
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
public class UserRepoImpl implements UserCustom {

  private final ElasticsearchTemplate template;

  @Autowired
  public UserRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }

  @Override
  public Page<UserPO> findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(String query, Pageable pageable) {
    Preconditions.checkNotNull(query);

    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
        .must(
            boolQuery()
                .should(wildcardQuery("username", wildcard(query)))
                .should(termQuery("username.pinyin", query))
                .should(termQuery("email", query))
                .should(termQuery("mobile", query))
                .should(termQuery("superId", query))
                .should(termQuery("tags", query)));

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(bool)
        .withPageable(pageable)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .build();
    return template.queryForPage(searchQuery, UserPO.class);
  }
}
