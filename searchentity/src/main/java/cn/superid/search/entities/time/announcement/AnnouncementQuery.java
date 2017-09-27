package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.PagedQuery;
import java.util.Date;
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
  private long startTime;
  private long endTime;

  public AnnouncementQuery() {
  }

  public AnnouncementQuery(List<Long> affairIds, String query,
      PageRequest pageRequest, long startTime, long endTime) {
    this.affairIds = affairIds;
    setQuery(query);
    this.startTime = startTime;
    this.endTime = endTime;
    setPageRequest(pageRequest);
  }

  public AnnouncementQuery(List<Long> affairIds, String query,
      PageRequest pageRequest) {
    this.affairIds = affairIds;
    setQuery(query);
    this.startTime = 0;
    this.endTime = new Date().getTime();
    setPageRequest(pageRequest);
  }

  public List<Long> getAffairIds() {
    return affairIds;
  }

  public void setAffairIds(List<Long> affairIds) {
    this.affairIds = affairIds;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }
}
