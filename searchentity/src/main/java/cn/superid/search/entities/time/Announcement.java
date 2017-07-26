package cn.superid.search.entities.time;

import cn.superid.search.entities.Tag;
import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement", type = "announcement", refreshInterval = "1s")
public class Announcement {

  @Id
  @Field(type = FieldType.String, index = FieldIndex.no)
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

  @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second_millis, index = FieldIndex.no)
  private Timestamp modifyTime;
  @Field(type = FieldType.Long, index = FieldIndex.no)
  private long creatorUserId;
  @Field(type = FieldType.Long, index = FieldIndex.no)
  private long affairId;
  @Field(type = FieldType.Boolean, index = FieldIndex.no)
  private boolean isTop;
  @Field(type = FieldType.Integer, index = FieldIndex.no)
  private int type;
  @Field(type = FieldType.String, store = false, index = FieldIndex.no)
  private String entityMap;

  public Announcement() {
  }

  public Announcement(String id, String title, String content, List<Tag> tags, String creatorRole,
      String creatorUser) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.creatorRole = creatorRole;
    this.creatorUser = creatorUser;
  }

  public Announcement(String id, String title, String content, List<Tag> tags, String creatorRole,
      String creatorUser, long affairId, String affairName, Timestamp modifyTime,
      long creatorUserId, boolean isTop, int type, String entityMap) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.creatorRole = creatorRole;
    this.creatorUser = creatorUser;
    this.affairId = affairId;
    this.affairName = affairName;
    this.modifyTime = modifyTime;
    this.creatorUserId = creatorUserId;
    this.isTop = isTop;
    this.type = type;
    this.entityMap = entityMap;
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

  public long getAffairId() {
    return affairId;
  }

  public void setAffairId(long affairId) {
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

  public long getCreatorUserId() {
    return creatorUserId;
  }

  public void setCreatorUserId(long creatorUserId) {
    this.creatorUserId = creatorUserId;
  }

  public boolean isTop() {
    return isTop;
  }

  public void setTop(boolean top) {
    isTop = top;
  }

  public int getType() {
    return type;
  }

  public void setType(int type) {
    this.type = type;
  }

  public String getEntityMap() {
    return entityMap;
  }

  public void setEntityMap(String entityMap) {
    this.entityMap = entityMap;
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
        ", affairId=" + affairId +
        ", affairName='" + affairName + '\'' +
        ", modifyTime=" + modifyTime +
        ", creatorUserId=" + creatorUserId +
        ", isTop=" + isTop +
        ", type=" + type +
        ", entityMap='" + entityMap + '\'' +
        '}';
  }
}
