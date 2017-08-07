package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PageRequestDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class AffairQuery {

  private String query;
  private PageRequest pageRequest;

  public AffairQuery() {
  }

  public AffairQuery(String query, PageRequest pageRequest) {
    this.query = query;
    this.pageRequest = pageRequest;
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
