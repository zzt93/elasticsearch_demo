package cn.superid.search.entities.user.role;

import cn.superid.search.entities.PagedQuery;
import cn.superid.search.entities.TagVO;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class RoleQuery extends PagedQuery {

  private List<TagVO> tags;

  private final Long allianceId;

  public RoleQuery(String query, PageRequest pageRequest, Long allianceId) {
    this.allianceId = allianceId;
    setQuery(query);
    setPageRequest(pageRequest);
  }

  public RoleQuery(List<TagVO> tags, PageRequest pageRequest, Long allianceId) {
    this.tags = tags;
    this.allianceId = allianceId;
    setPageRequest(pageRequest);
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public List<TagVO> getTags() {
    return tags;
  }

  @Override
  public String toString() {
    return "RoleQuery{" +
        "tags=" + tags +
        ", allianceId=" + allianceId +
        ", pagedQuery=" + super.toString() +
        '}';
  }
}
