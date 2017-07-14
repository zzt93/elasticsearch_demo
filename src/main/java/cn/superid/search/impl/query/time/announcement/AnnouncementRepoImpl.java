package cn.superid.search.impl.query.time.announcement;

import cn.superid.search.entities.time.Announcement;
import cn.superid.search.impl.query.QueryHelper;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

/**
 * @author zzt
 */
public class AnnouncementRepoImpl implements AnnouncementCustom {

    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public Page<Announcement> findByTitleOrModifierRoleOrModifierUserOrTagsIn(String info, Pageable pageable) {
        String query = QueryHelper.replacePlaceholders(FIND_BY_TITLE_OR_CONTENT_OR_MODIFIER_ROLE_OR_MODIFIER_USER_OR_TAGS_IN_QUERY, info);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(wrapperQuery(query))
                .withHighlightFields(new HighlightBuilder.Field("title"), new HighlightBuilder.Field("content"))
                .build();
        return template.queryForPage(searchQuery, Announcement.class);
    }

}
