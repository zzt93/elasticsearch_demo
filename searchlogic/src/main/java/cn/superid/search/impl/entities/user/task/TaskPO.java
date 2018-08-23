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
  @Field(type = FieldType.keyword)
  private String fromName;
  @Field(type = FieldType.Long)
  private long[] roles;
  @Field(type = FieldType.Byte)
  private Byte state;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Long)
  private Long allianceId;
  @Field(type = FieldType.Long)
  private Long annId;
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

  public Long getAnnId() {
    return annId;
  }

  public void setAnnId(Long annId) {
    this.annId = annId;
  }

  public String getFromName() {
    return fromName;
  }

  public void setFromName(String fromName) {
    this.fromName = fromName;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
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

  public long[] getRoles() {
    return roles;
  }

  public void setRoles(long[] roles) {
    this.roles = roles;
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
