package cn.superid.search.impl.query.time.announcement;

import cn.superid.search.entities.time.Announcement;
import org.elasticsearch.search.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.elasticsearch.index.query.QueryBuilders.wrapperQuery;

/**
 * @author zzt
 */
public class AnnouncementRepoImpl implements AnnouncementCustom {
    private static final Pattern PARAMETER_PLACEHOLDER = Pattern.compile("\\?(\\d+)");

    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public Page<Announcement> findByTitleOrModifierRoleOrModifierUserOrTagsIn(String info, Pageable pageable) {
        String query = replacePlaceholders(FIND_BY_TITLE_OR_CONTENT_OR_MODIFIER_ROLE_OR_MODIFIER_USER_OR_TAGS_IN_QUERY, info);
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(wrapperQuery(query))
                .withHighlightFields(new HighlightBuilder.Field("title"), new HighlightBuilder.Field("content"))
                .build();
        return template.queryForPage(searchQuery, Announcement.class);
    }

    private String replacePlaceholders(String input, String... var) {
        Matcher matcher = PARAMETER_PLACEHOLDER.matcher(input);
        String result = input;
        while (matcher.find()) {
            String group = matcher.group();
            int index = Integer.parseInt(matcher.group(1));
            result = result.replace(group, var[index]);
        }
        return result;
    }

}
