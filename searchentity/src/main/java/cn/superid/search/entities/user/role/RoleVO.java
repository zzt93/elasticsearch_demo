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

  public RoleVO() {
  }

  public RoleVO(String id, String title, Boolean deprecated, Long affairId, Long taskId) {
    this.id = id;
    this.title = title;
    this.deprecated = deprecated;
    this.affairId = affairId;
    this.taskId = taskId;
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

  @Override
  public String toString() {
    return "RolePO{" +
        "id='" + id + '\'' +
        ", title='" + title + '\'' +
        ", deprecated=" + deprecated +
        ", affairId=" + affairId +
        '}';
  }

  public String indexSuffix() {
    return null;
  }
}
