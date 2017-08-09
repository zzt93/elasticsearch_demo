package cn.superid.search.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class PagedQuery extends StringQuery {

  private PageRequest pageRequest;

  public PageRequest getPageRequest() {
    return pageRequest;
  }

  @JsonDeserialize(converter = PageRequestDeserializer.class)
  public void setPageRequest(PageRequest pageRequest) {
    this.pageRequest = pageRequest;
  }
}
