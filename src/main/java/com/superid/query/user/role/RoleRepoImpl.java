package com.superid.query.user.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by zzt on 17/6/27.
 *
 * The name of this class have to match the core repository interface or won't add customized
 *
 * @see RoleRepo
 * @see RoleRepoImpl
 */
public class RoleRepoImpl implements RoleCustom {
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public Page<Role> findRoleExcept(Long alliance, String query, Pageable pageable) {
        //        template.queryForPage()
        return null;
    }

    @Override
    public Page<Role> findRoleInterAlliance(String query, Pageable pageable) {
        return null;
    }
}
