package com.superid.query.dynamic.announcement;

import com.superid.query.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by zzt on 17/6/21.
 */
public interface AnnouncementRepo extends ElasticsearchRepository<Announcement, String> {

    //    List<Announcement> findAllByTitle(String title, Pageable pageable);
//    Page<Announcement> findByTitle(String title, Pageable pageable);
//    Slice<Announcement> findByTitle(String title, Pageable pageable); // not work
//    Page<Announcement> findAllByTitle(String title, Pageable pageable);

    Page<Announcement> findAllByTitleOrPublisherOrModifierOrTagsIn(String title, String publisher, String modifier, List<Tag> tags, Pageable pageable);

    @Query("{\"bool\" : {\"should\" : [ {\"match\" : {\"?0\" : \"?1\"}} ]}}")
    Page<Announcement> findByAll(String field, String info, Pageable pageable);
}
