package cn.superid.search.impl.entities.user.file;

import java.util.List;
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
  List<FilePO> findByTitleOrUploadRole(String info, Pageable pageable);
}