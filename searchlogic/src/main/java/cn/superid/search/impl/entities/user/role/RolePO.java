package cn.superid.search.impl.entities.user.role;

import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.impl.entities.TagPO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
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
@Document(indexName = "role-#{suffix.toString()}", type = "role", createIndex = false)
public class RolePO {

  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.text, analyzer = "smartcn")
  private String title;
  @Field(type = FieldType.Boolean)
  private Boolean deprecated;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;


  public RolePO() {
  }

  public RolePO(String id, String title, Boolean deprecated, Long affairId, Long taskId) {
    this.id = id;
    this.title = title;
    this.deprecated = deprecated;
    this.affairId = affairId;
  }

  public RolePO(RoleVO entity) {
    id = entity.getId();
    title = entity.getTitle();
    deprecated = entity.getDeprecated();
    affairId = entity.getAffairId();
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

  public List<TagPO> getTags() {
    return tags;
  }

  public void setTags(List<TagPO> tags) {
    this.tags = tags;
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
