package cn.superid.search.entities.time;

import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class AnnouncementQuery {

  private List<Long> affairIds;
  private String query;
  private PageRequest pageRequest;

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

  public void setPageRequest(PageRequest pageRequest) {
    this.pageRequest = pageRequest;
  }
}
