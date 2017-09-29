package cn.superid.search.impl.entities.user.warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/27.
 */
public interface MaterialRepo extends ElasticsearchRepository<MaterialPO, String> {

  @Query(" {" +
      " \"bool\": {\n" +
      "     \"should\": [\n" +
      "       {\n" +
      "         \"match\": {\n" +
      "           \"query\": \"?0\",\n" +
      "           \"fields\": [\"name\"]\n" +
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
  Page<MaterialPO> findByNameOrTagsIn(String info, Pageable pageable);
}
