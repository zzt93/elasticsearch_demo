package com.superid.query.precreate.file;

import com.superid.query.dynamic.announcement.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface FileRepo extends ElasticsearchRepository<File, String> {


    @Query("{\"bool\" : {\"must\" : [ {\"match\" : {\"?0\" : \"?1\"}} ]}}")
    Page<File> findByAll(String field, String info, Pageable pageable);
}