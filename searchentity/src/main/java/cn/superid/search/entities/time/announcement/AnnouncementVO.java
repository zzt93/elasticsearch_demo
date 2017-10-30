package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.TagVO;
import cn.superid.search.entities.time.TimeBasedIndex;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by zzt on 17/5/27.
 */
public class AnnouncementVO implements TimeBasedIndex {

  private String id;
  private String title;
  private String content;
  private List<TagVO> tagVOS;
  private String creatorRole;
  private String affairName;

  private Long affairId;

  private Timestamp modifyTime;
  private Long creatorRoleId;

  private Timestamp createTime;

  public AnnouncementVO() {
  }

  public AnnouncementVO(String id, String title, String content, List<TagVO> tagVOS, String creatorRole,
      String creatorUser, Long affairId) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tagVOS = tagVOS;
    this.creatorRole = creatorRole;
    this.affairId = affairId;
  }

  public AnnouncementVO(String id, String title, String content, List<TagVO> tagVOS, String creatorRole,
       Long creatorRoleId, Long affairId, String affairName,
      Timestamp modifyTime,
      Timestamp createTime) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.tagVOS = tagVOS;
    this.creatorRole = creatorRole;
    this.creatorRoleId = creatorRoleId;
    this.affairId = affairId;
    this.affairName = affairName;
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

  public List<TagVO> getTagVOS() {
    return tagVOS;
  }

  public void setTagVOS(List<TagVO> tagVOS) {
    this.tagVOS = tagVOS;
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

  public void setCreatorRoleId(Long creatorRoleId) {
    this.creatorRoleId = creatorRoleId;
  }

  @Override
  public String toString() {
    return "AnnouncementVO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", content='" + content + '\'' +
        ", tagVOS=" + tagVOS +
        ", creatorRole='" + creatorRole + '\'' +
        ", affairName='" + affairName + '\'' +
        ", modifyTime=" + modifyTime +
        ", creatorRoleId=" + creatorRoleId +
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
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
    return simpleDateFormat.format(createTime);
  }
}
