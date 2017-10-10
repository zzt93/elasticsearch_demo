package cn.superid.search.impl.entities.time.announcement;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface AnnouncementCustom {

  String FIND_BY_TITLE_OR_CONTENT_OR_MODIFIER_ROLE_OR_MODIFIER_USER_OR_TAGS_IN_QUERY =
"{\n"
    + "  \"bool\": {\n"
    + "    \"filter\": {\n"
    + "      \"terms\": {\n"
    + "        \"affairId\": ?1"
    + "      }\n"
    + "    }, \n"
    + "    \"must\": [\n"
    + "      {\n"
    + "        \"range\": {\n"
    + "          \"modifyTime\": {\n"
    + "            \"gt\": \"?2\", \n"
    + "            \"lte\": \"?3\", \n"
    + "            \"format\": \"yyyy-MM-dd hh:mm:ss\"\n"
    + "          }\n"
    + "        }\n"
    + "      }, \n"
    + "      {\n"
    + "        \"bool\": {\n"
    + "          \"should\": [\n"
    + "            {\n"
    + "              \"multi_match\": {\n"
    + "                \"query\": \"?0\", \n"
    + "                \"fields\": [\n"
    + "                  \"title\", \n"
    + "                  \"content\", \n"
    + "                  \"creatorRole\", \n"
    + "                  \"creatorUser\", \n"
    + "                  \"affairName\"\n"
    + "                ]\n"
    + "              }\n"
    + "            }, \n"
    + "            {\n"
    + "              \"nested\": {\n"
    + "                \"path\": \"tags\", \n"
    + "                \"query\": {\n"
    + "                  \"match\": {\n"
    + "                    \"tags.des\": \"?0\"\n"
    + "                  }\n"
    + "                }\n"
    + "              }\n"
    + "            }\n"
    + "          ]\n"
    + "        }\n"
    + "      }\n"
    + "    ]\n"
    + "  }\n"
    + "}\n";

  Page<AnnouncementPO> findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(
      List<Long> affairIds, String info,
      Pageable pageable);

  Page<AnnouncementPO> findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsWithTimeInAffair(
      List<Long> affairIds, String info, long startTime, long endTime,
      Pageable pageable);
}
