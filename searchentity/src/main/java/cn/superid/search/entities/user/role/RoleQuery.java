package cn.superid.search.entities.user.role;

import cn.superid.search.entities.EsField;
import cn.superid.search.entities.PagedQuery;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
@Data
public class RoleQuery extends PagedQuery {

  private String[] tags;

  private Long affairId;
  private Long allianceId;

  private EsField inTagsField;

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

}
