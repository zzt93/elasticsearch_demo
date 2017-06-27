package com.superid.query.time.announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface AnnouncementRepo extends ElasticsearchRepository<Announcement, String> {

    //    List<Announcement> findAllByTitle(String title, Pageable pageable);
//    Page<Announcement> findByTitle(String title, Pageable pageable);
//    Slice<Announcement> findByTitle(String title, Pageable pageable); // not work
//    Page<Announcement> findAllByTitle(String title, Pageable pageable);

    @Query(" {" +
            " \"bool\": {\n" +
            "     \"should\": [\n" +
            "       {\n" +
            "         \"multi_match\": {\n" +
            "           \"query\": \"?0\",\n" +
            "           \"fields\": [\"title\", \"publisher\", \"modifier\"]\n" +
            "         }\n" +
            "       },\n" +
            "       {\n" +
            "         \"nested\": {\n" +
            "           \"path\": \"tags\",\n" +
            "           \"query\": {\n" +
            "             \"match\": {\n" +
            "               \"tags.des\": \"?0\"\n" +
            "             }}\n" +
            "         }\n" +
            "       } ]\n" +
            "  }" +
            "}")
    Page<Announcement> findAllByTitleOrPublisherOrModifierOrTagsIn(String info, Pageable pageable);

    @Query("{\"bool\" : {\"should\" : [ {\"match\" : {\"?0\" : \"?1\"}} ]}}")
    Page<Announcement> findByAll(String field, String info, Pageable pageable);
}
