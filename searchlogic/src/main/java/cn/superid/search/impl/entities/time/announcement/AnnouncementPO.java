package cn.superid.search.impl.entities.time.announcement;

import cn.superid.search.impl.entities.time.TimeBasedIndex;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement-#{suffix.toString()}", type = "announcement", createIndex = false, shards = 1, replicas = 0)
@Mapping(mappingPath = "mapping/announcement.json")
public class AnnouncementPO implements TimeBasedIndex {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String title;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String content;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.Long)
  private Long affairId;

  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Long)
  private long[] roles;
  @Field(type = FieldType.Byte)
  private Byte plateType;
  @Field(type = FieldType.Byte)
  private Byte type;

  public AnnouncementPO() {
  }

  /**
   * For test
   */
  public AnnouncementPO(String id, String title, String content, String[] tags,
      long[] roles,
      String creatorUser, Long affairId, Timestamp modifyTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.affairId = affairId;
    this.modifyTime = modifyTime;
    this.roles = roles;
  }

  public long[] getRoles() {
    return roles;
  }

  public Byte getPlateType() {
    return plateType;
  }

  public void setPlateType(Byte plateType) {
    this.plateType = plateType;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public void setRoles(long[] roles) {
    this.roles = roles;
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

  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  @Override
  public String toString() {
    return "Announcement{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", tags=" + Arrays.toString(tags) +
        ", modifyTime=" + modifyTime +
        ", affairId=" + affairId +
        '}';
  }

  @Override
  public int timeFormatLen() {
    return "yyyy-MM".length();
  }

  @Override
  public String indexSuffix() {
    return null;
  }
}