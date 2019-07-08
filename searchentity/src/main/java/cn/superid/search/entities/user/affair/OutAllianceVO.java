package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.user.user.UserVO;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzt
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OutAllianceVO {

  private List<AllianceVO> affairVOPageVO;
  private List<UserVO> userVO;


}
