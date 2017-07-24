package cn.superid.search.impl.query.user.role;

import cn.superid.search.entities.user.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zzt on 17/6/27.
 */
public interface RoleCustom {

    Page<Role> findRoleExcept(Long alliance, String query, Pageable pageable);
    Page<Role> findRoleInterAlliance(String query, Pageable pageable);

}
