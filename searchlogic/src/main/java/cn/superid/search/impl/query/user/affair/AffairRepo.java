package cn.superid.search.impl.query.user.affair;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/29.
 */
public interface AffairRepo extends ElasticsearchRepository<Affair, String> {

  Affair findById(String id);

  @Query(" {" +
      "\"multi_match\": {\n" +
      "        \"query\":    \"?0\",\n" +
      "        \"fields\":   [ \"name^2\", \"path\" ]\n" +
      "    }" +
      "}")
  Page<Affair> findByNameOrPath(String info, Pageable pageable);
}
