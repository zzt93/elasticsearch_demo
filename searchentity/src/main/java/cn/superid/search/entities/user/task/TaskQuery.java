package cn.superid.search.entities.user.task;

import cn.superid.search.entities.PagedQuery;
import java.util.List;

/**
 * @author zzt
 */
public class TaskQuery extends PagedQuery {

  private Long affairId;
  private Long targetId;
  private List<Byte> types;
  private List<Byte> states;
  private List<Long> roles;

  public TaskQuery() {
  }

  public List<Byte> getTypes() {
    return types;
  }

  public void setTypes(List<Byte> types) {
    this.types = types;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Long getTargetId() {
    return targetId;
  }

  public void setTargetId(Long targetId) {
    this.targetId = targetId;
  }

  public List<Long> getRoles() {
    return roles;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }

  public List<Byte> getStates() {
    return states;
  }

  public void setStates(List<Byte> states) {
    this.states = states;
  }
}
