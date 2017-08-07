package cn.superid.search.impl.entities.time.announcement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface AnnouncementRepo extends ElasticsearchRepository<AnnouncementPO, String>,
    AnnouncementCustom {

  //    List<AnnouncementPO> findAllByTitle(String title, Pageable pageable);
//    Page<AnnouncementPO> findByTitle(String title, Pageable pageable);
//    Slice<AnnouncementPO> findByTitle(String title, Pageable pageable); // not work
//    Page<AnnouncementPO> findAllByTitle(String title, Pageable pageable);

  @Query("{\"bool\" : {\"should\" : [ {\"match\" : {\"?0\" : \"?1\"}} ]}}")
  Page<AnnouncementPO> findByAll(String field, String info, Pageable pageable);
}
