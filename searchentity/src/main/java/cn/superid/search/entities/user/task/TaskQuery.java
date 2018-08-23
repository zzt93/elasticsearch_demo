package cn.superid.search.entities.user.task;

import cn.superid.search.entities.PagedQuery;
import java.util.List;

/**
 * @author zzt
 */
public class TaskQuery extends PagedQuery {

  private Byte state;
  private List<Long> roles;

  public List<Long> getRoles() {
    return roles;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }

  public TaskQuery() {
  }

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "TaskQuery{" +
        "super=" + super.toString() +
        "state=" + state +
        ", roles=" + roles +
        '}';
  }
}
