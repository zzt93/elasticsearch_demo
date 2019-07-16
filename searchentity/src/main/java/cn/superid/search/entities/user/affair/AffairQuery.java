package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PagedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 * @see PagedQuery
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AffairQuery extends PagedQuery {
  private String[] tags;
  private long allianceId;

  public AffairQuery() {
  }

  public AffairQuery(String query, PageRequest pageRequest) {
    setQuery(query);
    setPageRequest(pageRequest);
  }

  public AffairQuery(String query, PageRequest pageRequest, String[] tags) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.tags = tags;
  }

  public long getAllianceId() {
    return allianceId;
  }

  public AffairQuery setAllianceId(long allianceId) {
    this.allianceId = allianceId;
    return this;
  }

}
