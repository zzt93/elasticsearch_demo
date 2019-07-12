package cn.superid.search.impl.entities.time.announcement;

import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.impl.query.HighlightMapper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.text.SimpleDateFormat;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;
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
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AnnouncementRepoImpl implements AnnouncementCustom {

  private static final int TITLE_BOOST = 10000;
  private final ElasticsearchTemplate template;
  private final ElasticsearchConverter elasticsearchConverter;

  @Override
  public Page<AnnouncementPO> findByTitleOrContentOrTags(AnnouncementQuery info,
      Pageable pageable) {
    Preconditions.checkArgument(pageable != null);
    Preconditions.checkArgument(info.getAllianceId() != null && info.getAllianceId() != 0);
    String query = info.getQuery();
    Preconditions.checkArgument(query != null);
    Preconditions.checkArgument(info.getAffairIds() != null);

    BoolQueryBuilder should = boolQuery()
        .should(wildcardQuery("title", wildcard(query)).boost(TITLE_BOOST))
        .should(matchQuery("content", query).boost(1));

    try {
      int number = Integer.parseInt(query);
      should.should(termQuery("number", number).boost(100 * TITLE_BOOST));
    } catch (NumberFormatException ignored) {}
    BoolQueryBuilder bool = boolQuery().must(should);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    bool.filter(rangeQuery("modifyTime")
        .gt(dateFormat.format(new Date(info.getStartTime())))
        .lt(dateFormat.format(new Date(info.getEndTime()))));

    TermsQueryBuilder affairId = termsQuery("affairId", info.getAffairIds());
    String indexName;
    if (info.isExcludeAffair()) {
      indexName = Suffix.indexNamePattern(AnnouncementPO.class);
      bool.mustNot(affairId);
    } else {
      indexName = Suffix.indexName(AnnouncementPO.class,
          info.getAllianceId() / AnnouncementPO.CLUSTER_SIZE);
      // TODO 18/6/7 other repo also need
      if (!template.indexExists(indexName)) {
        return null;
      }
      bool.filter(affairId)
          .filter(termQuery("allianceId", info.getAllianceId()));
    }
    if (info.getRoleIds() != null) {
      bool.filter(termsQuery("roles", info.getRoleIds()));
    }
    if (info.getPlateType() != null) {
      bool.filter(termQuery("plateType", info.getPlateType()));
    }
    if (info.getPlateSubType() != null) {
      bool.filter(termQuery("plateSubType", info.getPlateSubType()));
    }
    if (info.getStates() != null) {
      bool.filter(termsQuery("state", info.getStates()));
    }
    if (info.getTargetId() != null) {
      bool.filter(termQuery("targetId", info.getTargetId()));
    }

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(indexName)
        .withQuery(bool)
        .withPageable(pageable)
        .withHighlightFields(new HighlightBuilder.Field("title"),
            new HighlightBuilder.Field("content"))
        .build();
    return template
        .queryForPage(searchQuery, AnnouncementPO.class,
            new HighlightMapper<AnnouncementPO>(elasticsearchConverter,
                (highlightFields, announcement) -> {
                  HighlightField title = highlightFields.get("title");
                  if (title != null) {
                    announcement.setTitle(announcement.getTitle().replaceAll(query, "<em>" + query + "</em>"));
                  }
                  HighlightField content = highlightFields.get("content");
                  if (content != null) {
                    announcement.setThumbContent(content.fragments()[0].toString());
                  }
                }));
  }
}
