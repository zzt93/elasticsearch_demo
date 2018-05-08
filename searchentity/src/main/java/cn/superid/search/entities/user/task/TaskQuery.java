package cn.superid.search.entities.user.task;

import cn.superid.search.entities.PagedQuery;

/**
 * @author zzt
 */
public class TaskQuery extends PagedQuery {

  private Byte state;
  private Long userId;

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public TaskQuery() {
  }

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }
}
