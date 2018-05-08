package cn.superid.search.impl.entities.user.task;

import cn.superid.search.entities.user.task.TaskVO;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/21.
 */
@Document(indexName = "task-#{suffix.toString()}", type = "task", refreshInterval = "1s", createIndex = false, shards = 1)
public class TaskPO {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword)
  private String title;
  @Field(type = FieldType.Long)
  private long[] users;
  @Field(type = FieldType.Byte)
  private Byte state;
  @Field(type = FieldType.Long)
  private Long allianceId;
  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp offTime;

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

  public long[] getUsers() {
    return users;
  }

  public void setUsers(long[] users) {
    this.users = users;
  }

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Timestamp getOffTime() {
    return offTime;
  }

  public void setOffTime(Timestamp offTime) {
    this.offTime = offTime;
  }
}
