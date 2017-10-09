package cn.superid.search.entities.user.role;

import cn.superid.search.entities.user.UserBasedIndex;

/**
 * Created by zzt on 17/6/21.
 */

public class RoleVO implements UserBasedIndex {


  private String id;
  private String title;
  private Long affairId;

  private Long allianceId;

  public RoleVO() {
  }

  public RoleVO(String id, String title, Long affairId, Long allianceId) {
    this.id = id;
    this.title = title;
    this.affairId = affairId;
    this.allianceId = allianceId;
  }

  public RoleVO(String id) {
    this.id = id;
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
        ", affairId=" + affairId +
        ", allianceId=" + allianceId +
        '}';
  }

  public String indexSuffix() {
    return allianceId.toString();
  }
}
