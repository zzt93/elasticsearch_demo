package cn.superid.search.impl.entities.user.affair;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/29.
 */
public interface AffairRepo extends ElasticsearchRepository<AffairPO, String> {

  Optional<AffairPO> findById(String id);

  Page<AffairPO> findByName(String name, Pageable pageable);

  Page<AffairPO> findBySuperId(String superId, Pageable pageable);

  Page<AffairPO> findByTagsIn(String[] tags, Pageable pageable);

  @Query(" {" +
      " \"bool\": { " +
      "     \"should\": [\n" +
      "       {\n" +
      "         \"multi_match\": {" +
      "            \"query\":    \"?0\",\n" +
      "            \"fields\":   [ \"name\", \"superId\"]\n" +
      "          }" +
      "       },\n" +
      "       {\n" +
      "         \"nested\": {\n" +
      "           \"path\": \"tags\",\n" +
      "           \"query\": {\n" +
      "             \"match\": {\n" +
      "               \"tags.des\": \"?0\"\n" +
      "             }}\n" +
      "         }\n" +
      "       } " +
      "     ]\n" +
      "  }" +
      "}")
  Page<AffairPO> findAny(String info, Pageable pageable);
}
