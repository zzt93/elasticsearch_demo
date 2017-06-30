package com.superid.query.user.affair;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/29.
 */
public interface AffairRepo extends ElasticsearchRepository<Affair, String> {

    Affair findById(String id);

    Page<Affair> findByNameOrPath(String name, String path, Pageable pageable);
}
