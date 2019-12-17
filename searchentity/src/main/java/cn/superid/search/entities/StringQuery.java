package cn.superid.search.entities;

import lombok.Data;

/**
 * The query class encapsulate a single string search query
 *
 * @author zzt
 */
@Data
public class StringQuery {

  /**
   * Query string to search
   */
  private String query;
  private VisibleContext visibleContext;

}
