package cn.superid.search.impl.entities.user.role;

import cn.superid.search.impl.entities.TagPO;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface RoleRepo extends ElasticsearchRepository<RolePO, String>, RoleCustom {

  Page<RolePO> findByTagsIn(List<TagPO> tags, Pageable pageable);

  @Query(" {" +
      " \"bool\": {\n" +
      "     \"should\": [\n" +
      "       {\n" +
      "         \"match\": {\n" +
      "           \"query\": \"?0\",\n" +
      "           \"fields\": [\"title\"]\n" +
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
      "}")
  Page<RolePO> findByTagsInOrTitle(String query, Pageable pageRequest);

  Page<RolePO> findByAffairIdAndTitle(Long affairId, String title, Pageable pageable);

  List<RolePO> findByAffairIdAndTitle(Long affairId, String title);

  Page<RolePO> findByTitleAndAffairIdNot(String title, Long affairId, Pageable pageable);
}
