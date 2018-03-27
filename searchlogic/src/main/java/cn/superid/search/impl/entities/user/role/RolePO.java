package cn.superid.search.impl.entities.user.role;

import cn.superid.search.entities.user.role.RoleVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
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
@Document(indexName = "role-#{suffix.toString()}", type = "role", createIndex = false, shards = 1, replicas = 0)
public class RolePO {

  public static final int CLUSTER_SIZE = 1000;
  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.keyword)
  private String title;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Long)
  private Long userId;
  @Field(type = FieldType.keyword)
  private String affairName;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Byte)
  private Byte publicType;
  @Field(type = FieldType.Long)
  private Long ownerRoleId;
  @Field(type = FieldType.keyword)
  private String ownerRoleTitle;
  @Field(type = FieldType.Long)
  private Long allianceId;

  public RolePO() {
  }
  /**
   * for test
   */
  RolePO(String id, String title, Long affairId, Byte type,
      String[] t, Long ownerRoleId, long allianceId) {
    this.id = id;
    this.title = title;
    this.affairId = affairId;
    this.type = type;
    this.tags = t;
    this.ownerRoleId = ownerRoleId;
    this.allianceId = allianceId;
  }


  public RolePO(RoleVO entity) {
    id = entity.getId();
    title = entity.getTitle();
    affairId = entity.getAffairId();
  }

  public Byte getPublicType() {
    return publicType;
  }

  public RolePO setPublicType(Byte publicType) {
    this.publicType = publicType;
    return this;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
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

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
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

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "RolePO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", affairId=" + affairId +
        ", tags=" + Arrays.toString(tags) +
        ", type=" + type +
        ", ownerRoleId=" + ownerRoleId +
        '}';
  }
}