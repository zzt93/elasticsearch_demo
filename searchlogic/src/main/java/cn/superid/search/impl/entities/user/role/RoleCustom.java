package cn.superid.search.impl.entities.user.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zzt on 17/6/27.
 */
public interface RoleCustom {

  Page<RolePO> findRoleExcept(Long alliance, String query, Pageable pageable);

  Page<RolePO> findRoleInterAlliance(String query, Pageable pageable);

  Page<RolePO> findByAll(String query);
}
