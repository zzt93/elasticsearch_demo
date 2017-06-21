package com.superid.query.precreate.role;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface RoleRepo extends ElasticsearchRepository<Role, String> {

    Role findById(String id);

    Slice<Role> findAllByTitle(String title, Pageable pageable);

    Slice<Role> findAllByAffair(String affair, Pageable pageable);
}
