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

  private Long allianceId;
  private List<Long> affairIds;
  private Long targetId;
  private boolean excludeAffair = false;
  private List<Long> roleIds;
  private long startTime;
  private long endTime;
  private List<Byte> states;
  private Byte plateType;
  private Integer plateSubType;
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

  public AnnouncementQuery(List<Long> affairIds, long alliance, String query,
      PageRequest pageRequest, List<Long> roles) {
    this.affairIds = affairIds;
    setQuery(query);
    this.startTime = 0;
    this.endTime = new Date().getTime();
    allianceId = alliance;
    setPageRequest(pageRequest);
    setRoleIds(roles);
  }

  public Long getTargetId() {
    return targetId;
  }

  public void setTargetId(Long targetId) {
    this.targetId = targetId;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  public boolean isExcludeAffair() {
    return excludeAffair;
  }

  public void setExcludeAffair(boolean excludeAffair) {
    this.excludeAffair = excludeAffair;
  }

  public List<Byte> getStates() {
    return states;
  }

  public void setStates(List<Byte> states) {
    this.states = states;
  }

  public Byte getPlateType() {
    return plateType;
  }

  public void setPlateType(Byte plateType) {
    this.plateType = plateType;
  }

  public Integer getPlateSubType() {
    return plateSubType;
  }

  public void setPlateSubType(Integer plateSubType) {
    this.plateSubType = plateSubType;
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
