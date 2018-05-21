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
public class AnnouncementRepoImpl implements AnnouncementCustom {

  @Autowired
  private ElasticsearchTemplate template;
  @Autowired
  private ElasticsearchConverter elasticsearchConverter;

  @Override
  public Page<AnnouncementPO> findByTitleOrContentOrTags(AnnouncementQuery info,
      Pageable pageable) {
    Preconditions.checkArgument(pageable != null);
    Preconditions.checkArgument(info.getAllianceId() != null && info.getAllianceId() != 0);
    Preconditions.checkArgument(info.getQuery() != null);
    Preconditions.checkArgument(info.getAffairIds() != null);

    BoolQueryBuilder bool = boolQuery()
        .must(
            boolQuery()
                .should(wildcardQuery("title", wildcard(info.getQuery())).boost(10))
                .should(termQuery("tags", info.getQuery()).boost(5))
                .should(matchQuery("thumbContent", info.getQuery()).boost(2))
                .should(matchQuery("content", info.getQuery()).boost(1))
        )
        .must(termQuery("allianceId", info.getAllianceId()));
    TermsQueryBuilder affairId = termsQuery("affairId", info.getAffairIds());
    if (info.isExcludeAffair()) {
      bool.mustNot(affairId);
    } else {
      bool.filter(affairId);
    }
    if (info.getRoleIds() != null) {
      bool.filter(termsQuery("roles", info.getRoleIds()));
    }
    if (info.getStartTime() != 0 && info.getEndTime() != 0) {
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
      bool.filter(rangeQuery("modifyTime")
          .gte(dateFormat.format(new Date(info.getStartTime())))
          .lte(dateFormat.format(new Date(info.getEndTime()))));
    }
    if (info.getPlateType() != null) {
      bool.filter(termQuery("plateType", info.getPlateType()));
    }
    if (info.getState() != null) {
      bool.filter(termQuery("state", info.getState()));
    }

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexName(AnnouncementPO.class,
            info.getAllianceId() / AnnouncementPO.CLUSTER_SIZE))
        .withQuery(bool)
        .withPageable(pageable)
        .withHighlightFields(new HighlightBuilder.Field("title"),
            new HighlightBuilder.Field("thumbContent"))
        .build();
    return template
        .queryForPage(searchQuery, AnnouncementPO.class,
            new HighlightMapper<AnnouncementPO>(elasticsearchConverter,
                (highlightFields, announcement) -> {
                  HighlightField title = highlightFields.get("title");
                  if (title != null) {
                    announcement.setTitle(title.fragments()[0].toString());
                  }
                  HighlightField content = highlightFields.get("thumbContent");
                  if (content != null) {
                    announcement.setThumbContent(content.fragments()[0].toString());
                  }
                }));
  }
}
