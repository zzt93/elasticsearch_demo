package cn.superid.search.entities.time.announcement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 * Created by zzt on 17/5/27.
 */
public class AnnouncementVO {

  private String id;
  private String title;
  private String content;
  private String[] tagVOS;

  private Long affairId;

  private Timestamp modifyTime;

  private Timestamp createTime;

  public AnnouncementVO() {
  }

  public AnnouncementVO(String id, String title, String content, String[] tagVOS,
      Long affairId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tagVOS = tagVOS;
    this.affairId = affairId;
  }

  public AnnouncementVO(String id, String title, String content, String[] tagVOS,
      Long affairId,
      Timestamp modifyTime,
      Timestamp createTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tagVOS = tagVOS;
    this.affairId = affairId;
    this.modifyTime = modifyTime;
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

  public String[] getTagVOS() {
    return tagVOS;
  }

  public void setTagVOS(String[] tagVOS) {
    this.tagVOS = tagVOS;
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
    return "AnnouncementVO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", tagVOS=" + Arrays.toString(tagVOS) +
        ", modifyTime=" + modifyTime +
        ", affairId=" + affairId +
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
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");
    return simpleDateFormat.format(createTime);
  }
}
