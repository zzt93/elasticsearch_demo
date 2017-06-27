package com.superid.query.user.role;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by zzt on 17/6/27.
 */
public interface RoleCustom {

    Page<Role> findRoleExcept(Long alliance, String query, Pageable pageable);
    Page<Role> findRoleInterAlliance(String query, Pageable pageable);

}
