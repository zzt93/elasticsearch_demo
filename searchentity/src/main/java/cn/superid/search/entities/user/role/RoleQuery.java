package cn.superid.search.entities.user.role;

import cn.superid.search.entities.PagedQuery;
import java.util.Arrays;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class RoleQuery extends PagedQuery {

  private String[] tags;

  private Long allianceId;

  public RoleQuery() {
  }

  public RoleQuery(String query, PageRequest pageRequest, Long allianceId) {
    this.allianceId = allianceId;
    setQuery(query);
    setPageRequest(pageRequest);
  }

  public RoleQuery(String[] tags, PageRequest pageRequest, Long allianceId) {
    this.tags = tags;
    this.allianceId = allianceId;
    setPageRequest(pageRequest);
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public String[] getTags() {
    return tags;
  }

  @Override
  public String toString() {
    return "RoleQuery{" +
        "tags=" + Arrays.toString(tags) +
        ", allianceId=" + allianceId +
        ", pagedQuery=" + super.toString() +
        '}';
  }
}
