package cn.superid.search.impl.query.time.announcement;

import cn.superid.search.entities.time.announcement.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface AnnouncementRepo extends ElasticsearchRepository<Announcement, String>,
    AnnouncementCustom {

  //    List<Announcement> findAllByTitle(String title, Pageable pageable);
//    Page<Announcement> findByTitle(String title, Pageable pageable);
//    Slice<Announcement> findByTitle(String title, Pageable pageable); // not work
//    Page<Announcement> findAllByTitle(String title, Pageable pageable);

  @Query("{\"bool\" : {\"should\" : [ {\"match\" : {\"?0\" : \"?1\"}} ]}}")
  Page<Announcement> findByAll(String field, String info, Pageable pageable);
}
