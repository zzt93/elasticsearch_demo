package cn.superid.search.impl.entities.time.announcement;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

import cn.superid.search.impl.query.HighlightMapper;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
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
public class AnnouncementRepoImpl implements AnnouncementCustom {

  @Autowired
  private ElasticsearchTemplate template;
  @Autowired
  private ElasticsearchConverter elasticsearchConverter;

  @Override
  public Page<AnnouncementPO> findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(List<Long> affairIds,
      String info, Pageable pageable) {
    return findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsWithTimeInAffair(affairIds, info, 0, new Date().getTime(), pageable);
  }

  @Override
  public Page<AnnouncementPO> findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsWithTimeInAffair(
      List<Long> affairIds, String info, long startTime, long endTime,
      Pageable pageable) {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    String query = QueryHelper.replacePlaceholders(
        FIND_BY_TITLE_OR_CONTENT_OR_MODIFIER_ROLE_OR_MODIFIER_USER_OR_TAGS_IN_QUERY, info,
        affairIds.toString(), dateFormat.format(new Date(startTime)), dateFormat.format(new Date(endTime)));
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.timeBasedPattern(AnnouncementPO.class, startTime, endTime))
        .withQuery(wrapperQuery(query))
        .withPageable(pageable)
        .withHighlightFields(new HighlightBuilder.Field("title"),
            new HighlightBuilder.Field("content"))
        .build();
    return template
        .queryForPage(searchQuery, AnnouncementPO.class,
            new HighlightMapper<AnnouncementPO>(elasticsearchConverter, (highlightFields, announcement) -> {
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
