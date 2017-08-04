package cn.superid.search.impl.query.time.announcement;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.time.announcement.Announcement;
import cn.superid.search.impl.query.HighlightMapper;
import cn.superid.search.impl.query.QueryHelper;
import java.util.List;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.elasticsearch.search.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * @author zzt
 */
public class AnnouncementRepoImpl implements AnnouncementCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<Announcement> findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(
      List<Long> affairIds, String info,
      Pageable pageable) {
    String query = QueryHelper.replacePlaceholders(
        FIND_BY_TITLE_OR_CONTENT_OR_MODIFIER_ROLE_OR_MODIFIER_USER_OR_TAGS_IN_QUERY, info,
        affairIds.toString());
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(RollingIndex.indexNamePattern(Announcement.class))
        .withQuery(wrapperQuery(query))
        .withPageable(pageable)
        .withHighlightFields(new HighlightBuilder.Field("title"),
            new HighlightBuilder.Field("content"))
        .build();
    return template
        .queryForPage(searchQuery, Announcement.class,
            new HighlightMapper<Announcement>((highlightFields, announcement) -> {
              HighlightField title = highlightFields.get("title");
              if (title != null) {
                announcement.setTitle(title.fragments()[0].toString());
              }
              HighlightField content = highlightFields.get("content");
              if (content != null) {
                announcement.setContent(content.fragments()[0].toString());
              }
            }));
  }

}
