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

  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String title;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String affairName;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;
  @Field(type = FieldType.Integer)
  private Integer type;
  @Field(type = FieldType.Long)
  private Long ownerRoleId;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String ownerRoleTitle;


  public RolePO() {
  }

  /**
   * for test
   */
  RolePO(String id, String title, Long affairId, Integer type,
      List<TagPO> t, Long ownerRoleId) {
    this.id = id;
    this.title = title;
    this.affairId = affairId;
    this.type = type;
    this.tags = t;
    this.ownerRoleId = ownerRoleId;
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

  public Long getOwnerRoleId() {
    return ownerRoleId;
  }

  public void setOwnerRoleId(Long ownerRoleId) {
    this.ownerRoleId = ownerRoleId;
  }

  public String getAffairName() {
    return affairName;
  }

  public void setAffairName(String affairName) {
    this.affairName = affairName;
  }

  public String getOwnerRoleTitle() {
    return ownerRoleTitle;
  }

  public void setOwnerRoleTitle(String ownerRoleTitle) {
    this.ownerRoleTitle = ownerRoleTitle;
  }

  @Override
  public String toString() {
    return "RolePO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", affairId=" + affairId +
        ", tags=" + tags +
        ", type=" + type +
        ", ownerRoleId=" + ownerRoleId +
        '}';
  }
}
