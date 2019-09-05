package cn.superid.search.entities;

import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * This class is used to hold the searched result
 *
 * @author zzt
 */
@Data
public class PageVO<T> {

  private String scrollId;
  private List<T> content;
  private Long totalElements;
  private Integer totalPages;
  private Integer pageSize;
  private boolean hasMore;

  public PageVO(List<T> content, Long totalElements, Integer totalPages, Integer pageSize) {
    this.content = content;
    this.totalElements = totalElements;
    this.totalPages = totalPages;
    this.pageSize = pageSize;
    hasMore = content.size() == pageSize;
  }
  public PageVO(List<T> content, Long totalElements, Integer pageSize) {
    this.content = content;
    this.totalElements = totalElements;
    this.pageSize = pageSize;
    hasMore = content.size() == pageSize;
  }

  public PageVO() {
    totalElements = 0L;
    totalPages = 0;
    content = Collections.emptyList();
    hasMore = false;
  }

  public static <T> PageVO<T> empty() {
    return new PageVO<>();
  }


  public <R> PageVO(Page<R> page, Function<R, T> mapper,
      PageRequest pageRequest) {
    this(page, mapper, pageRequest, null);
  }

  public <R> PageVO(Page<R> page, Function<R, T> mapper,
      PageRequest pageRequest, String scrollId) {
    if (page == null) {
      return;
    }
    if (scrollId != null) {
      this.scrollId = scrollId;
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

  public boolean isHasMore() {
    return hasMore;
  }

  public void setHasMore(boolean hasMore) {
    this.hasMore = hasMore;
  }

}
