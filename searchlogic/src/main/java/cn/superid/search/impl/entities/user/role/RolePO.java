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
 * Created by zzt on 17/6/21.
 *
 * Suffix is allianceId
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
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;
  @Field(type = FieldType.Integer)
  private Integer type;


  public RolePO() {
  }

  RolePO(String id, String title, Long affairId, Integer type,
      List<TagPO> t) {
    this.id = id;
    this.title = title;
    this.affairId = affairId;
    this.type = type;
    this.tags = t;
  }

  public RolePO(RoleVO entity) {
    id = entity.getId();
    title = entity.getTitle();
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

  public List<TagPO> getTags() {
    return tags;
  }

  public void setTags(List<TagPO> tags) {
    this.tags = tags;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return "RolePO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", affairId=" + affairId +
        ", tags=" + tags +
        ", type=" + type +
        '}';
  }
}
