package cn.superid.search.impl.query.time.announcement;

import cn.superid.search.entities.time.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface AnnouncementCustom {

    String FIND_BY_TITLE_OR_CONTENT_OR_MODIFIER_ROLE_OR_MODIFIER_USER_OR_TAGS_IN_QUERY =
            " {" +
                    " \"bool\": {\n" +
                    "     \"should\": [\n" +
                    "       {\n" +
                    "         \"multi_match\": {\n" +
                    "           \"query\": \"?0\",\n" +
                    "           \"fields\": [\"title\", \"content\", \"creatorRole\", \"creatorUser\", \"affairName\"]\n" +
                    "         }\n" +
                    "       },\n" +
                    "       {\n" +
                    "         \"nested\": {\n" +
                    "           \"path\": \"tags\",\n" +
                    "           \"query\": {\n" +
                    "             \"match\": {\n" +
                    "               \"tags.des\": \"?0\"\n" +
                    "             }}\n" +
                    "         }\n" +
                    "       } ]\n" +
                    "  }" +
                    "}";

    Page<Announcement> findByTitleOrModifierRoleOrModifierUserOrTagsIn(String info, Pageable pageable);
}
