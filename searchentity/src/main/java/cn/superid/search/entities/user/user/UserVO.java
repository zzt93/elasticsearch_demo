package cn.superid.search.entities.user.user;

import cn.superid.search.entities.user.UserBasedIndex;

/**
 * Created by zzt on 17/6/5.
 */
public class UserVO implements UserBasedIndex {

  private String id;
  private String username;
  private String mobile;

  public UserVO() {
  }

  public UserVO(String id) {
    this.id = id;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getMobile() {
    return mobile;
  }

  public UserVO setMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public String indexSuffix() {
    return "";
  }
}
