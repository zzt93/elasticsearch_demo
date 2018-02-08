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
  private boolean excludeAffair = false;
  private List<Long> roleIds;
  private long startTime;
  private long endTime;
  private Byte state;
  private Byte plateType;

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
      PageRequest pageRequest, List<Long> roles) {
    this.affairIds = affairIds;
    setQuery(query);
    this.startTime = 0;
    this.endTime = new Date().getTime();
    setPageRequest(pageRequest);
    setRoleIds(roles);
  }

  public boolean isExcludeAffair() {
    return excludeAffair;
  }

  public void setExcludeAffair(boolean excludeAffair) {
    this.excludeAffair = excludeAffair;
  }

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }

  public Byte getPlateType() {
    return plateType;
  }

  public void setPlateType(Byte plateType) {
    this.plateType = plateType;
  }

  public List<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(List<Long> roleIds) {
    this.roleIds = roleIds;
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

  @Override
  public String toString() {
    return "AnnouncementQuery{" +
        "affairIds=" + affairIds +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", pagedQuery=" + super.toString() +
        '}';
  }
}
