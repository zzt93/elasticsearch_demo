package cn.superid.search.entities;

import java.util.List;
import org.springframework.data.domain.Page;

/**
 * This class is used to hold the searched result
 * @author zzt
 */
public class PageVO<T> {

  private List<T> content;
  private long totalElements;
  private int totalPages;
  private int pageSize;

  public PageVO() {
  }


  public PageVO(Page<T> page) {
    if (page == null) {
      return;
    }
    this.content = page.getContent();
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.pageSize = page.getSize();
  }

  /**
   * Returns the number of total pages.
   *
   * @return the number of total pages
   */
  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  /**
   * Returns the total amount of elements.
   *
   * @return the total amount of elements
   */
  public long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(long totalElements) {
    this.totalElements = totalElements;
  }

  public int getPageSize() {
    return pageSize;
  }

  public void setPageSize(int pageSize) {
    this.pageSize = pageSize;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }
}
