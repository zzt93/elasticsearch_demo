package cn.superid.search.impl.entities.user.user;

import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zzt
 */
@Data
@Document(indexName = "personal_info", type = "personal_info", refreshInterval = "1s", createIndex = false, shards = 1, replicas = 0)
public class PersonalInfo {

  private String id;
  private long affairId;
  private long userId;
  private Short type;
  private String content;
  private String description;

}
