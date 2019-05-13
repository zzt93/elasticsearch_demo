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
public class AuditQuery extends PagedQuery {

  private long sender;
  private long receiver;
  private List<Byte> states;

  public AuditQuery() {
  }

  public AuditQuery(String query, PageRequest pageRequest, List<Byte> states, long sender, long receiver) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.states = states;
    this.sender = sender;
    this.receiver = receiver;
  }

}
