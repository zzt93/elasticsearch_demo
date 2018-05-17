package cn.superid.search.impl.entities.user.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface RoleRepo extends ElasticsearchRepository<RolePO, String>, RoleCustom {

  Page<RolePO> findByTagsIn(String[] tags, Pageable pageable);

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
      "         \"match\": {\"tags\": \"?0\"}" +
      "       } ]\n" +
      "  }" +
      "}")
  Page<RolePO> findByTagsInOrTitle(String query, Pageable pageRequest);

  Page<RolePO> findByAffairIdAndTitle(Long affairId, String title, Pageable pageable);


  Page<RolePO> findByTitleAndAffairIdNot(String title, Long affairId, Pageable pageable);
}
