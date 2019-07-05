package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.user.user.UserVO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenkorVO {

  private PageVO<AffairVO> affairVOPageVO;
  private UserVO userVO;


}
