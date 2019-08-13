package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.user.UserBasedIndex;
import lombok.Data;

/**
 * @author zzt
 */
@Data
public class AffairVO implements UserBasedIndex {

  private String parentId;
  private String id;
  private String name;
  private String superId;
  private String[] tagVOS;
  private Byte state;

  private Long allianceId;
  private Byte mold;

  public AffairVO() {
  }

  public AffairVO(String parentId, String id, Byte state) {
    this.parentId = parentId;
    this.id = id;
    this.state = state;
  }

  public AffairVO(String parentId, String id, String name, String superId,
      String[] tagVOS, Byte state, Long allianceId) {
    this.parentId = parentId;
    this.id = id;
    this.name = name;
    this.superId = superId;
    this.tagVOS = tagVOS;
    this.state = state;
    this.allianceId = allianceId;
  }



  public String indexSuffix() {
    return allianceId.toString();
  }

}
