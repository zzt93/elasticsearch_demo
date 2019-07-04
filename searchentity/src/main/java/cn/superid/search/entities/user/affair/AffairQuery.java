package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PagedQuery;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 * @see PagedQuery
 */
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

  @Override
  public String toString() {
    return "AffairQuery{"
        + super.toString()
        + "}";
  }

  public long getAllianceId() {
    return allianceId;
  }

  public AffairQuery setAllianceId(long allianceId) {
    this.allianceId = allianceId;
    return this;
  }

  public String[] getTags() {
    return tags;
  }
}
