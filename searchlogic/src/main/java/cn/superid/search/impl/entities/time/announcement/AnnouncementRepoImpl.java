package cn.superid.search.impl.entities.time.announcement;

import static cn.superid.search.impl.query.esUtil.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.common.rest.type.auth.PermissionCategory;
import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.entities.time.announcement.AnnouncementQuery.AnnType;
import cn.superid.search.entities.time.announcement.MyAnnQuery;
import cn.superid.search.impl.entities.VisibleFilter;
import cn.superid.search.impl.query.esUtil.DefaultFetchSource;
import cn.superid.search.impl.query.esUtil.HighlightMapper;
import cn.superid.search.impl.query.rolling.Suffix;
import cn.superid.search.impl.util.TimeUtil;
import com.google.common.base.Preconditions;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.apache.lucene.search.join.ScoreMode;
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
  private final VisibleFilter visibleFilter;

  @Override
  public Page<AnnouncementPO> findByTitleOrContentOrTags(AnnouncementQuery info,
      Pageable pageable) {
    Preconditions.checkArgument(pageable != null);
    Preconditions.checkArgument(info.getAllianceId() != null && info.getAllianceId() != 0);
    String query = info.getQuery();
    Preconditions.checkArgument(query != null);
    Preconditions.checkArgument(info.getVisibleContext().getAffairs() != null);

    BoolQueryBuilder should = boolQuery()
        .should(wildcardQuery("title", wildcard(query)).boost(TITLE_BOOST))
        .should(matchQuery("content", query).boost(1));

    try {
      int number = Integer.parseInt(query);
      should.should(termQuery("number", number).boost(100 * TITLE_BOOST));
    } catch (NumberFormatException ignored) {
    }
    BoolQueryBuilder bool = boolQuery().must(should);

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    bool.filter(rangeQuery("modifyTime")
        .gt(dateFormat.format(new Date(info.getStartTime())))
        .lt(dateFormat.format(new Date(info.getEndTime()))));

    TermsQueryBuilder affairId = termsQuery("affairId", info.getVisibleContext().getAffairs());
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
      BoolQueryBuilder roles = boolQuery().filter(termsQuery("roles.role_id", info.getRoleIds()));
      bool.filter(nestedQuery("roles", roles, ScoreMode.Avg));
    }
    if (info.getTypes() != null && info.getTypes().size() != 0) {
      BoolQueryBuilder types = annTypeFilter(info.getTypes());
      bool.filter(types);
    }
    if (info.getStates() != null) {
      bool.filter(termsQuery("state", info.getStates()));
    }
    if (info.getTargetId() != null) {
      bool.filter(termQuery("targetId", info.getTargetId()));
    }
    if (!info.isExcludeAffair()) {
      visibleFilter.get(info.getVisibleContext(), PermissionCategory.ANNOUNCEMENT);
    }

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(indexName)
        .withQuery(bool)
        .withPageable(pageable)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .withHighlightFields(new HighlightBuilder.Field("title"),
            new HighlightBuilder.Field("content").fragmentSize(50))
        .build();
    return template
        .queryForPage(searchQuery, AnnouncementPO.class,
            new HighlightMapper<AnnouncementPO>(elasticsearchConverter,
                (highlightFields, announcement) -> {
                  HighlightField title = highlightFields.get("title");
                  if (title != null) {
                    announcement.setTitle(
                        HighlightMapper.keywordHighlight(query, title.fragments()[0].toString()));
                  }
                  HighlightField content = highlightFields.get("content");
                  if (content != null) {
                    announcement.setThumbContent(content.fragments()[0].toString());
                  }
                }));
  }

  @Override
  public Page<AnnouncementPO> myAnn(MyAnnQuery info, Pageable pageable) {
    String query = info.getQuery();

    BoolQueryBuilder bool = boolQuery();

    if (query != null) {
      BoolQueryBuilder should = boolQuery();
      try {
        int number = Integer.parseInt(query);
        should.should(termQuery("number", number).boost(100 * TITLE_BOOST));
      } catch (NumberFormatException ignored) {
        should.should(wildcardQuery("title", wildcard(query)).boost(TITLE_BOOST));
      }
      bool.should(should);
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    if (info.getCreate() != null) {
      bool.filter(TimeUtil.timeRange(info.getCreate().setTimeFieldName("createTime"), dateFormat));
    }
    if (info.getModify() != null) {
      bool.filter(TimeUtil.timeRange(info.getModify().setTimeFieldName("modifyTime"), dateFormat));
    }
    if (info.getStates() != null) {
      bool.filter(termsQuery("state", info.getStates()));
    }

    BoolQueryBuilder roles = boolQuery().filter(termsQuery("roles.role_id", info.getSelfRoles()));
    if (info.getRoleTypes() != null) {
      roles.filter(termsQuery("roles.type", info.getRoleTypes()));
    }
    bool
        .filter(nestedQuery("roles", roles, ScoreMode.Avg))
        .filter(annTypeFilter(info.getTypes()));

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(AnnouncementPO.class))
        .withQuery(bool)
        .withPageable(pageable)
        .withSourceFilter(DefaultFetchSource.fields("_id", "roles"))
        .build();
    return template.queryForPage(searchQuery, AnnouncementPO.class);
  }

  private BoolQueryBuilder annTypeFilter(List<AnnType> types) {
    BoolQueryBuilder filter = boolQuery();
    types.forEach(i -> {
      if (i.getPlateType() != null && i.getPlateSubType() != null) {
        BoolQueryBuilder type = boolQuery();
        type.filter(termQuery("plateType", i.getPlateType()));
        type.filter(termQuery("plateSubType", i.getPlateSubType()));
        filter.should(type);
      }
    });
    return filter;
  }


}
