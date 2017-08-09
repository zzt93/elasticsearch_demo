package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.PagedQuery;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * The class encapsulates the query of {@link AnnouncementVO}
 *
 * @author zzt
 * @see AnnouncementVO
 */
public class AnnouncementQuery extends PagedQuery {

  private List<Long> affairIds;

  public AnnouncementQuery() {
  }

  public AnnouncementQuery(List<Long> affairIds, String query,
      PageRequest pageRequest) {
    this.affairIds = affairIds;
    setQuery(query);
    setPageRequest(pageRequest);
  }

  public List<Long> getAffairIds() {
    return affairIds;
  }

  public void setAffairIds(List<Long> affairIds) {
    this.affairIds = affairIds;
  }


}
