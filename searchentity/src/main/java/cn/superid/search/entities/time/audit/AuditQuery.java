package cn.superid.search.entities.time.audit;

import cn.superid.search.entities.PagedQuery;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class AuditQuery extends PagedQuery {

  private List<Long> roles;
  private Byte state;

  public AuditQuery() {
  }

  public AuditQuery(String query, PageRequest pageRequest, List<Long> roles, Byte state) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.roles = roles;
    this.state = state;
  }

  public List<Long> getRoles() {
    return roles;
  }

  public void setRoles(List<Long> roles) {
    this.roles = roles;
  }

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }
}
