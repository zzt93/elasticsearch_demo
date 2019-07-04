package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.user.user.UserVO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author zzt
 */
@Data
@AllArgsConstructor
public class OutAllianceVO {

  private List<AllianceVO> affairVOPageVO;
  private List<UserVO> userVO;


}
