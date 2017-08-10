package cn.superid.search.entities.user.role;

import cn.superid.search.entities.user.UserBasedIndex;

/**
 * Created by zzt on 17/6/21.
 */

public class RoleVO implements UserBasedIndex {


  private String id;
  private String title;
  private Boolean deprecated;
  private Long affairId;
  private Long taskId;

  private Long allianceId;

  public RoleVO() {
  }

  public RoleVO(String id, String title, Boolean deprecated, Long affairId, Long taskId,
      Long allianceId) {
    this.id = id;
    this.title = title;
    this.deprecated = deprecated;
    this.affairId = affairId;
    this.taskId = taskId;
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

  public Boolean getDeprecated() {
    return deprecated;
  }

  public void setDeprecated(Boolean deprecated) {
    this.deprecated = deprecated;
  }

  public Long getTaskId() {
    return taskId;
  }

  public void setTaskId(Long taskId) {
    this.taskId = taskId;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  @Override
  public String toString() {
    return "RoleVO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", deprecated=" + deprecated +
        ", affairId=" + affairId +
        ", taskId=" + taskId +
        ", allianceId=" + allianceId +
        '}';
  }

  public String indexSuffix() {
    return allianceId.toString();
  }
}
