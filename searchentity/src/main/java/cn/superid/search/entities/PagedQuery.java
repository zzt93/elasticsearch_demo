package cn.superid.search.entities;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
@Data
public class PagedQuery extends StringQuery {

  private String scrollId;
  /**
   * Page info, json example:
   * <pre>
   *   {
   *     pageNumber: 0, // page number, count from 0
   *     pageSize: 10, // the size of a single page
   *     sort: {
   *       orders: [
   *        {property: "time", direction: "DESC", nullHandling: "NULLS_LAST", ignoreCase: "false"}
   *       ]
   *     }
   *   }
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

  @Override
  public String toString() {
    return "PagedQuery{" +
        "pageRequest=" + pageRequest.toString() +
        ", stringQuery=" + super.toString() +
        '}';
  }
}
