package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.UserBasedIndex;
import java.util.List;

/**
 * @author zzt
 */
public class AffairVO implements UserBasedIndex {

  private String parentId;
  private String id;
  private String name;
  private String superId;
  private List<Tag> tags;
  private Integer state;

  private Long allianceId;

  public AffairVO() {
  }

  public AffairVO(String parentId, String id, Integer state) {
    this.parentId = parentId;
    this.id = id;
    this.state = state;
  }

  public AffairVO(String parentId, String id, String name, String superId,
      List<Tag> tags, Integer state, Long allianceId) {
    this.parentId = parentId;
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

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
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

  @Override
  public String toString() {
    return "AffairVO{" +
        "parentId='" + parentId + '\'' +
        ", id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", superId='" + superId + '\'' +
        ", tags=" + tags +
        ", state=" + state +
        ", allianceId=" + allianceId +
        '}';
  }
}
