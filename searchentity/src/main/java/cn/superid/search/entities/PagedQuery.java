package cn.superid.search.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import org.springframework.data.domain.PageRequest;

/**
 * <p>
 * The class encapsulate a paged {@link StringQuery}
 * </p>
 * <ul>
 *   <li>page count string from 0</li>
 *   <li>size represent a single page size</li>
 * </ul>
 * @author zzt
 */
public class PagedQuery extends StringQuery {

  /**
   * Page info, now only used
   * <pre>
   *   int page; // page number, count from 0
   *   int size; // the size of a single page
   * </pre>
   */
  private PageRequest pageRequest;

  public PageRequest getPageRequest() {
    return pageRequest;
  }

  @JsonDeserialize(converter = PageRequestDeserializer.class)
  public void setPageRequest(PageRequest pageRequest) {
    this.pageRequest = pageRequest;
  }


}
