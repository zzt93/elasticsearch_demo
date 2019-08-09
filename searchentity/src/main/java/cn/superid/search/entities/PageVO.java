package cn.superid.search.entities;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * This class is used to hold the searched result
 *
 * @author zzt
 */
public class PageVO<T> {

  private String scrollId;
  private List<T> content;
  private Long totalElements;
  private Integer totalPages;
  private Integer pageSize;
  private boolean hasMore;

  public PageVO() {
    totalElements = 0L;
    totalPages = 0;
    content = Collections.emptyList();
  }


  public <R> PageVO(Page<R> page, Function<R, T> mapper,
      PageRequest pageRequest) {
    if (page == null) {
      return;
    }
    this.content = page.getContent().stream().map(mapper).collect(Collectors.toList());
    this.totalElements = page.getTotalElements();
    this.pageSize = pageRequest.getPageSize();
    this.totalPages = Math.toIntExact((totalElements + pageSize - 1) / pageSize);
    hasMore = content.size() == pageSize;
  }

  public <R> PageVO(Page<R> page, Function<R, T> mapper, String scrollId) {
    if (page == null) {
      return;
    }
    this.content = page.getContent().stream().map(mapper).collect(Collectors.toList());
    this.totalElements = page.getTotalElements();
    this.totalPages = page.getTotalPages();
    this.pageSize = page.getSize();
    this.scrollId = scrollId;
    hasMore = content.size() == pageSize;
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

  public String getScrollId() {
    return scrollId;
  }

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }
}
