package cn.superid.search.entities.user.user;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.UserBasedIndex;
import java.util.List;

/**
 * Created by zzt on 17/6/5.
 */
public class UserVO implements UserBasedIndex {

  private String id;
  private String realname;
  private String username;
  private String role;

  private List<Tag> tags;
  private Long affairId;
  private String mainAffair;

  public String getMainAffair() {
    return mainAffair;
  }

  public void setMainAffair(String mainAffair) {
    this.mainAffair = mainAffair;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
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

  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String indexSuffix() {
    return null;
  }
}
