package cn.superid.search.impl.query.user.warehouse;

import cn.superid.search.entities.user.Material;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/27.
 */
public interface MaterialRepo extends ElasticsearchRepository<Material, String> {

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
  Page<Material> findByTitleOrTagsIn(String info, Pageable pageable);
}
