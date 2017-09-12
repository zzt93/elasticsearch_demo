package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.TagVO;
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
  private List<TagVO> tagVOS;
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
      List<TagVO> tagVOS, Integer state, Long allianceId) {
    this.parentId = parentId;
    this.id = id;
    this.name = name;
    this.superId = superId;
    this.tagVOS = tagVOS;
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

  public List<TagVO> getTagVOS() {
    return tagVOS;
  }

  public void setTagVOS(List<TagVO> tagVOS) {
    this.tagVOS = tagVOS;
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
        ", tagVOS=" + tagVOS +
        ", state=" + state +
        ", allianceId=" + allianceId +
        '}';
  }
}
