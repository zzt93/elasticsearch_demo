package cn.superid.search.impl.entities.user.target;

import cn.superid.search.entities.user.target.TargetQuery;
import java.util.List;

/**
 * @author zzt
 */
public interface TargetCustom {

  List<TargetPO> findByNameAndAffairIdIn(
      TargetQuery targetQuery);

}
