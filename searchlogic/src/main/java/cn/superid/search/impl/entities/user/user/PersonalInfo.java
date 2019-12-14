package cn.superid.search.impl.entities.user.user;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zzt
 */
@Data
@Document(indexName = PersonalInfo.PERSONAL_INFO, type = "personal_info", refreshInterval = "1s", createIndex = false, shards = 1, replicas = 0)
public class PersonalInfo {

  public static final String PERSONAL_INFO = "personal_info";
  private String id;
  private long affairId;
  private long userId;
  private Short type;
  private String content;
  private String description;
  private Timestamp time;

  String getStudentDes() {
    Timestamp time = getTime();
    String yyyy = new SimpleDateFormat("yyyy").format(new Date(time.getTime()));
    return getContent() + yyyy + "级" + getDescription();
  }
}
