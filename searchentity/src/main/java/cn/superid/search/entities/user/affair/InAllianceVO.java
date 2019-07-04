package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.user.user.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zzt
 */
@Data
@AllArgsConstructor
public class InAllianceVO {

  private PageVO<AffairVO> affairVOPageVO;
  private PageVO<UserVO> userVO;

}
