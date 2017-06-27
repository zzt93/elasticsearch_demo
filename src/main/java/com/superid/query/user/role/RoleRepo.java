package com.superid.query.user.role;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface RoleRepo extends ElasticsearchRepository<Role, String>, RoleCustom {

    Slice<Role> findByTitle(String title, Pageable pageable);

    Slice<Role> findByAffairIdAndTitle(Long affairId, String title, Pageable pageable);

    Slice<Role> findByTitleAndAffairIdNot(String title, Long affairId, Pageable pageable);
}
