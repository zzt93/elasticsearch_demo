package cn.superid.search.impl.entities.time.announcement;

import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface AnnouncementCustom {

  Page<AnnouncementPO> findByTitleOrContentOrTags(AnnouncementQuery query, Pageable pageable);

}
