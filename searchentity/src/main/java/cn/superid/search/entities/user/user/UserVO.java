package cn.superid.search.entities.user.user;

import cn.superid.search.entities.user.UserBasedIndex;
import cn.superid.search.entities.user.affair.AffairVO;
import lombok.Data;

/**
 * Created by zzt on 17/6/5.
 */
@Data
public class UserVO implements UserBasedIndex {

  private String id;
  private long personalAffairId;
  private String username;
  private String mobile;

  public UserVO() {
  }

  public UserVO(String id) {
    this.id = id;
  }

  public UserVO(String id, Long personalAffairId) {
    this.id = id;
    this.personalAffairId = personalAffairId;
  }

  public UserVO setMobile(String mobile) {
    this.mobile = mobile;
    return this;
  }

  public String indexSuffix() {
    return "";
  }

  public AffairVO toAffairVO(int mold) {
    AffairVO res = new AffairVO();
    res.setId(getPersonalAffairId() + "");
    res.setMold(((byte) mold));
    return res;
  }
}
