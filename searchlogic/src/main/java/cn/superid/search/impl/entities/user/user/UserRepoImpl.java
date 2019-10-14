package cn.superid.search.impl.entities.user.user;

import static cn.superid.search.impl.query.HighlightMapper.setHighlight;
import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.common.rest.type.PublicType;
import cn.superid.search.impl.DefaultFetchSource;
import cn.superid.search.impl.query.HighlightMapper;
import com.google.common.base.Preconditions;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class UserRepoImpl implements UserCustom {

  private final ElasticsearchTemplate template;
  private final ElasticsearchConverter elasticsearchConverter;

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
                .should(
                    boolQuery()
                        .must(termQuery("mobile", query))
//                        .must(scriptQuery(new Script(" (doc['infoPublic'].value & 4) != 0")))
                        )
                .should(termQuery("superId", query))
                .should(termQuery("tags", query)));

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(bool)
        .withPageable(pageable)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .withHighlightFields(new HighlightBuilder.Field("username"),
            new HighlightBuilder.Field("username.pinyin"),
            new HighlightBuilder.Field("email"),
            new HighlightBuilder.Field("mobile"),
            new HighlightBuilder.Field("superId"),
            new HighlightBuilder.Field("tags")
                .fragmentSize(50))
        .build();
    return template.queryForPage(searchQuery, UserPO.class, new HighlightMapper<UserPO>(elasticsearchConverter,
        (highlightFields, user) -> {
          setHighlight(highlightFields.get("email"), h -> user.setEmail(h.fragments()[0].toString()));
          setHighlight(highlightFields.get("username"), h -> user.setUsername(HighlightMapper.keywordHighlight(query, h.fragments()[0].toString())));
          setHighlight(highlightFields.get("username.pinyin"), h -> user.setUsername(h.fragments()[0].toString()));
          setHighlight(highlightFields.get("mobile"), h -> user.setMobile(h.fragments()[0].toString()));
          setHighlight(highlightFields.get("superId"), h -> user.setSuperId(h.fragments()[0].toString()));
          setHighlight(highlightFields.get("tags"), h -> user.setTags(new String[]{h.fragments()[0].toString()}));
        }));
  }

  @Override
  public List<UserPO> findByMobileAndPublicType(String query) {
    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("publicType", PublicType.ALL))
//        .must(scriptQuery(new Script(" (doc['infoPublic'].value & 4) != 0")))
        .must(
            boolQuery()
                .should(termQuery("mobile", query)));

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(bool)
        .build();
    return template.queryForList(searchQuery, UserPO.class);
  }
}
