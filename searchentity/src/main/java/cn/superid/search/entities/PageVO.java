package cn.superid.search.entities;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;

/**
 * This class is used to hold the searched result
 *
 * @author zzt
 */
public class PageVO<T> {

  private List<T> content;
  private Long totalElements;
  private Integer totalPages;
  private Integer pageSize;

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

  public <R> PageVO(Page<R> page, Function<R, T> mapper) {
    if (page == null) {
      return;
    }
    this.content = page.getContent().stream().map(mapper).collect(Collectors.toList());
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.pageSize = page.getSize();
  }

  /**
   * Returns the number of total pages.
   *
   * @return the number of total pages
   */
  public Integer getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(Integer totalPages) {
    this.totalPages = totalPages;
  }

  /**
   * Returns the total amount of elements.
   *
   * @return the total amount of elements
   */
  public Long getTotalElements() {
    return totalElements;
  }

  public void setTotalElements(Long totalElements) {
    this.totalElements = totalElements;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public List<T> getContent() {
    return content;
  }

  public void setContent(List<T> content) {
    this.content = content;
  }
}
