package cn.superid.search.impl.query.user.file;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface FileRepo extends ElasticsearchRepository<FilePO, String> {

  @Query(" {" +
      "\"multi_match\": " +
      "    {\n" +
      "       \"query\": \"?0\",\n" +
      "       \"fields\": [\"title\", \"uploadRole\"]\n" +
      "    }\n" +
      "}")
  Page<FilePO> findByTitleOrUploadRole(String info, Pageable pageable);
}