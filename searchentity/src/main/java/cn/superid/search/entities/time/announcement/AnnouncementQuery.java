package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.PagedQuery;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;

/**
 * The class encapsulates the query of {@link AnnouncementVO}
 *
 * @author zzt
 * @see AnnouncementVO
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AnnouncementQuery extends PagedQuery {

  @Deprecated
  private List<Long> affairIds;

  private Long allianceId;
  private boolean excludeAffair = false;

  private Long targetId;
  private List<Long> roleIds;
  private long startTime;
  private long endTime;
  private List<Byte> states;
  private List<AnnType> types;

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

  public static class AnnType {
    private Integer plateType;
    private Integer plateSubType;

    public AnnType() {
    }

    public AnnType(Integer plateType, Integer plateSubType) {
      this.plateType = plateType;
      this.plateSubType = plateSubType;
    }

    public Integer getPlateType() {
      return plateType;
    }

    public void setPlateType(Integer plateType) {
      this.plateType = plateType;
    }

    public Integer getPlateSubType() {
      return plateSubType;
    }

    public void setPlateSubType(Integer plateSubType) {
      this.plateSubType = plateSubType;
    }
  }
}
