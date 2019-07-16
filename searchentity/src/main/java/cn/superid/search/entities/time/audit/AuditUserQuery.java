package cn.superid.search.entities.time.audit;

import cn.superid.search.entities.PagedQuery;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AuditUserQuery extends PagedQuery {

  private Long[] roles;
  private Long from;
  private Long to;
  private List<Byte> states;

  public AuditUserQuery() {
  }

  public AuditUserQuery(String query, PageRequest pageRequest, List<Byte> states, Long[] roles, Long from, Long to) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.states = states;
    this.roles = roles;
    this.from = from;
    this.to = to;
  }

}
