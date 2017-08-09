package cn.superid.search.impl.entities.time.task;

import cn.superid.search.entities.time.task.TaskVO;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/21.
 */
@Document(indexName = "task", type = "task", refreshInterval = "1s")
public class TaskPO {

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String title;

  public TaskPO() {
  }

  public TaskPO(TaskVO entity) {
    id = entity.getId();
    title = entity.getTitle();
  }

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

}
