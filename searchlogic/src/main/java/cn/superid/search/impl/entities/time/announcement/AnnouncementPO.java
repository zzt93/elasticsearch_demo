package cn.superid.search.impl.entities.time.announcement;

import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement-#{suffix.toString()}", type = "announcement", createIndex = false, shards = 1, replicas = 0)
public class AnnouncementPO {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String title;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String content;
  @Field(type = FieldType.Nested)
  private String[] tags;
  @Field(type = FieldType.Long)
  private Long creatorRoleId;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String creatorRole;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String affairName;

  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Byte)
  private Byte publicType;

  public AnnouncementPO() {
  }

  /**
   * For test
   */
  public AnnouncementPO(String id, String title, String content, String[] tags,
      String creatorRole,
      String creatorUser, Long affairId, Timestamp modifyTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.creatorRole = creatorRole;
    this.affairId = affairId;
    this.modifyTime = modifyTime;
  }


  public AnnouncementPO(String id, String title, String content, String[] tags,
      String creatorRole,
      String creatorUser, Long creatorRoleId, Long affairId, String affairName,
      Timestamp modifyTime,
      Long creatorUserId, Boolean isTop, Byte type, String entityMap,
      String avatar) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tags = tags;
    this.creatorRole = creatorRole;
    this.creatorRoleId = creatorRoleId;
    this.affairId = affairId;
    this.affairName = affairName;
    this.modifyTime = modifyTime;
  }

  public AnnouncementPO(AnnouncementVO vo) {
    id = vo.getId();
    title = vo.getTitle();
    content = vo.getContent();
    tags = VoAndPoConversion.toPOs(vo.getTagVOS());
    creatorRole = vo.getCreatorRole();
    creatorRoleId = vo.getCreatorRoleId();
    affairId = vo.getAffairId();
    affairName = vo.getAffairName();
    modifyTime = vo.getModifyTime();
  }

  public Byte getPublicType() {
    return publicType;
  }

  public AnnouncementPO setPublicType(Byte publicType) {
    this.publicType = publicType;
    return this;
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

  public Long getCreatorRoleId() {
    return creatorRoleId;
  }

  @Override
  public String toString() {
    return "Announcement{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", tags=" + tags +
        ", creatorRole='" + creatorRole + '\'' +
        ", affairName='" + affairName + '\'' +
        ", modifyTime=" + modifyTime +
        ", creatorRoleId=" + creatorRoleId +
        ", affairId=" + affairId +
        '}';
  }

}