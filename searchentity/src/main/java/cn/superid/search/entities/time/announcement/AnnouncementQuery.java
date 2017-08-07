package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.PageRequestDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * The class encapsulates the query of {@link AnnouncementVO}
 *
 * @author zzt
 * @see AnnouncementVO
 */
public class AnnouncementQuery {

  private List<Long> affairIds;
  private String query;
  private PageRequest pageRequest;

  public AnnouncementQuery() {
  }

  public AnnouncementQuery(List<Long> affairIds, String query,
      PageRequest pageRequest) {
    this.affairIds = affairIds;
    this.query = query;
    this.pageRequest = pageRequest;
  }

  public List<Long> getAffairIds() {
    return affairIds;
  }

  public void setAffairIds(List<Long> affairIds) {
    this.affairIds = affairIds;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public PageRequest getPageRequest() {
    return pageRequest;
  }

  @JsonDeserialize(converter = PageRequestDeserializer.class)
  public void setPageRequest(PageRequest pageRequest) {
    this.pageRequest = pageRequest;
  }

}
