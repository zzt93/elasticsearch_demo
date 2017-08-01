package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.time.TimeBasedIndex;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement-#{suffix.toString()}", type = "announcement", createIndex = false)
public class Announcement implements TimeBasedIndex {

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String title;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String content;
  @Field(type = FieldType.Nested)
  private List<Tag> tags;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String creatorRole;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String creatorUser;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String affairName;

  @Field(type = FieldType.Long, index = FieldIndex.not_analyzed)
  private Long affairId;

  @Field(type = FieldType.Date, index = FieldIndex.no, pattern = "YYYY-MM-DD HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Long, index = FieldIndex.no)
  private Long creatorUserId;
  @Field(type = FieldType.Long, index = FieldIndex.no)
  private Long creatorRoleId;
  @Field(type = FieldType.Boolean, index = FieldIndex.no)
  private Boolean isTop;
  @Field(type = FieldType.Integer, index = FieldIndex.no)
  private Integer type;
  @Field(type = FieldType.String, store = false, index = FieldIndex.no)
  private String entityMap;
  @Field(type = FieldType.String, index = FieldIndex.no)
  private String avatar;

  private Timestamp createTime;

  public Announcement() {
  }

  public Announcement(String id, String title, String content, List<Tag> tags, String creatorRole,
      String creatorUser, Long affairId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.creatorRole = creatorRole;
    this.creatorUser = creatorUser;
    this.affairId = affairId;
  }

  public Announcement(String id, String title, String content, List<Tag> tags, String creatorRole,
      String creatorUser, Long creatorRoleId, Long affairId, String affairName,
      Timestamp modifyTime,
      Long creatorUserId, Boolean isTop, Integer type, String entityMap,
      String avatar, Timestamp createTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.creatorRole = creatorRole;
    this.creatorUser = creatorUser;
    this.creatorRoleId = creatorRoleId;
    this.affairId = affairId;
    this.affairName = affairName;
    this.modifyTime = modifyTime;
    this.creatorUserId = creatorUserId;
    this.isTop = isTop;
    this.type = type;
    this.entityMap = entityMap;
    this.avatar = avatar;
    this.createTime = createTime;
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

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String getCreatorUser() {
    return creatorUser;
  }

  public void setCreatorUser(String creatorUser) {
    this.creatorUser = creatorUser;
  }

  public String getCreatorRole() {
    return creatorRole;
  }

  public void setCreatorRole(String creatorRole) {
    this.creatorRole = creatorRole;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public String getAffairName() {
    return affairName;
  }

  public void setAffairName(String affairName) {
    this.affairName = affairName;
  }

  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Long getCreatorUserId() {
    return creatorUserId;
  }

  public void setCreatorUserId(Long creatorUserId) {
    this.creatorUserId = creatorUserId;
  }

  public void setTop(Boolean top) {
    isTop = top;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }

  public String getEntityMap() {
    return entityMap;
  }

  public void setEntityMap(String entityMap) {
    this.entityMap = entityMap;
  }

  public Long getCreatorRoleId() {
    return creatorRoleId;
  }

  public void setCreatorRoleId(Long creatorRoleId) {
    this.creatorRoleId = creatorRoleId;
  }

  public Boolean getTop() {
    return isTop;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  @Override
  public String toString() {
    return "Announcement{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", tags=" + tags +
        ", creatorRole='" + creatorRole + '\'' +
        ", creatorUser='" + creatorUser + '\'' +
        ", affairName='" + affairName + '\'' +
        ", modifyTime=" + modifyTime +
        ", creatorUserId=" + creatorUserId +
        ", creatorRoleId=" + creatorRoleId +
        ", affairId=" + affairId +
        ", isTop=" + isTop +
        ", type=" + type +
        ", entityMap='" + entityMap + '\'' +
        ", avatar='" + avatar + '\'' +
        ", createTime=" + createTime +
        '}';
  }

  public Timestamp getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Timestamp createTime) {
    this.createTime = createTime;
  }

  public String indexSuffix() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    return simpleDateFormat.format(createTime);
  }
}
