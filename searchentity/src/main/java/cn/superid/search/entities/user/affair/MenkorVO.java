package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.user.user.UserVO;

/**
 * @author zzt
 */
public class MenkorVO {

  private PageVO<AffairVO> affairVOPageVO;
  private UserVO userVO;

  public MenkorVO() {
  }

  public MenkorVO(
      PageVO<AffairVO> affairVOPageVO, UserVO userVO) {
    this.affairVOPageVO = affairVOPageVO;
    this.userVO = userVO;
  }

  public PageVO<AffairVO> getAffairVOPageVO() {
    return affairVOPageVO;
  }

  public void setAffairVOPageVO(
      PageVO<AffairVO> affairVOPageVO) {
    this.affairVOPageVO = affairVOPageVO;
  }

  public UserVO getUserVO() {
    return userVO;
  }

  public void setUserVO(UserVO userVO) {
    this.userVO = userVO;
  }
}
