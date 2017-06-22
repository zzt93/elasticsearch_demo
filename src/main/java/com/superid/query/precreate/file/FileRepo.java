package com.superid.query.precreate.file;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface FileRepo extends ElasticsearchRepository<File, String> {


    Slice<File> findAllByTitleOrUploadRoleOrUploadUser(String title, String role, String user, Pageable pageable);
}