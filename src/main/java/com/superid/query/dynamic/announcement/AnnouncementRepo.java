package com.superid.query.dynamic.announcement;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface AnnouncementRepo extends ElasticsearchRepository<Announcement, String> {

    Slice<Announcement> findAllByTitleOrTagsOrPublisherOrModifier(String info, Pageable pageable);
}
