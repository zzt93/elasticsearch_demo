package cn.superid.search.impl.entities.time.announcement;

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
public class AnnouncementPO {
  public static final int CLUSTER_SIZE = 100;

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword)
  private String title;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String thumbContent;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String[] content;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Long)
  private Long allianceId;
  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Long)
  private long[] roles;
  @Field(type = FieldType.Byte)
  private Byte plateType;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Integer)
  private Integer number;

  public AnnouncementPO() {
  }

  /**
   * For test
   */
  public AnnouncementPO(String id, String title, String content, String[] tags,
      long[] roles,
      Long affairId, Timestamp modifyTime, long allianceId) {
    this.id = id;
    this.title = title;
    this.content = new String[]{content};
    this.tags = tags;
    this.affairId = affairId;
    this.modifyTime = modifyTime;
    this.roles = roles;
    this.allianceId = allianceId;
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

  public String[] getContent() {
    return content;
  }

  public void setContent(String[] content) {
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

  public String getThumbContent() {
    return thumbContent;
  }

  public void setThumbContent(String thumbContent) {
    this.thumbContent = thumbContent;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  @Override
  public String toString() {
    return "AnnouncementPO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", thumbContent='" + thumbContent + '\'' +
        ", content=" + Arrays.toString(content) +
        ", tags=" + Arrays.toString(tags) +
        ", affairId=" + affairId +
        ", allianceId=" + allianceId +
        ", modifyTime=" + modifyTime +
        ", roles=" + Arrays.toString(roles) +
        ", plateType=" + plateType +
        ", type=" + type +
        ", number=" + number +
        '}';
  }
}