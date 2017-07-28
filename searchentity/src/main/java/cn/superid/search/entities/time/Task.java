package cn.superid.search.entities.time;

import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/21.
 */
@Document(indexName = "task", type = "task", refreshInterval = "1s")
public class Task implements TimeBasedIndex {

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String title;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Timestamp getCreateTime() {
    return null;
  }

  public void setCreateTime(Timestamp createTime) {

  }

  public String indexSuffix() {
    return null;
  }
}
