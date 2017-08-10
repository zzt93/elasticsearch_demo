package cn.superid.search.impl.entities.user.role;

import cn.superid.search.entities.user.role.RoleVO;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Suffix is allianceId
 * Created by zzt on 17/6/21.
 *
 * @author zzt
 * @see cn.superid.search.impl.save.rolling.Suffix
 * @see RoleVO#indexSuffix()
 */
@Document(indexName = "role-#{suffix.toString()}", type = "role", shards = 10, createIndex = false)
public class RolePO {

  @Id
  private String id;

  @Field(type = FieldType.String, analyzer = "smartcn")
  private String title;
  @Field(type = FieldType.Boolean)
  private Boolean deprecated;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Long)
  private Long taskId;

  public RolePO() {
  }

  public RolePO(String id, String title, Boolean deprecated, Long affairId, Long taskId) {
    this.id = id;
    this.title = title;
    this.deprecated = deprecated;
    this.affairId = affairId;
    this.taskId = taskId;
  }

  public RolePO(RoleVO entity) {
    id = entity.getId();
    title = entity.getTitle();
    deprecated = entity.getDeprecated();
    affairId = entity.getAffairId();
    taskId = entity.getTaskId();
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
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

  public Boolean getDeprecated() {
    return deprecated;
  }

  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  @Override
  public String toString() {
    return "RolePO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", deprecated=" + deprecated +
        ", affairId=" + affairId +
        '}';
  }

}
