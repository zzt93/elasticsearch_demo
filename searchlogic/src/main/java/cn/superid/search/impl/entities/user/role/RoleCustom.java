package cn.superid.search.impl.entities.user.role;

import cn.superid.search.entities.user.role.RoleQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zzt on 17/6/27.
 */
public interface RoleCustom {

  Page<RolePO> findRoleExcept(Long alliance, String query, Pageable pageable);

  List<RolePO> findByAffairIdAndTitle(Long allianceId, Long affairId, String title);

  Page<RolePO> findByAll(RoleQuery query);
}
