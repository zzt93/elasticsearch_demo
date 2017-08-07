package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.UserBasedIndex;
import java.util.List;

/**
 * @author zzt
 */
public class AffairVO implements UserBasedIndex {

  private String fatherId;
  private String id;
  private String name;
  private String superId;
  private List<Tag> tags;
  private Integer state;

  private Long allianceId;

  public AffairVO() {
  }

  public AffairVO(String fatherId, String id) {
    this.fatherId = fatherId;
    this.id = id;
  }

  public AffairVO(String fatherId, String id, String name, String superId,
      List<Tag> tags, Integer state, Long allianceId) {
    this.fatherId = fatherId;
    this.id = id;
    this.name = name;
    this.superId = superId;
    this.tags = tags;
    this.state = state;
    this.allianceId = allianceId;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  public String getFatherId() {
    return fatherId;
  }

  public void setFatherId(String fatherId) {
    this.fatherId = fatherId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSuperId() {
    return superId;
  }

  public void setSuperId(String superId) {
    this.superId = superId;
  }

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String indexSuffix() {
    return allianceId.toString();
  }
}
