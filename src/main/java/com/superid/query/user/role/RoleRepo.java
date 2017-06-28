package com.superid.query.user.role;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface RoleRepo extends ElasticsearchRepository<Role, String>, RoleCustom {

    Page<Role> findByTaskIdAndTitle(Long taskId, String title, Pageable pageable);

    Page<Role> findByAffairIdAndTitle(Long affairId, String title, Pageable pageable);

    Page<Role> findByTitleAndAffairIdNot(String title, Long affairId, Pageable pageable);
}
