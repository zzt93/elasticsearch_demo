package com.superid.query.dynamic.announcement;

import com.superid.query.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by zzt on 17/6/21.
 */
public interface AnnouncementRepo extends ElasticsearchRepository<Announcement, String> {

    Slice<Announcement> findAllByTitleOrPublisherOrModifierOrTagsIn(String title, String publisher, String modifier, List<Tag> tags, Pageable pageable);
}
