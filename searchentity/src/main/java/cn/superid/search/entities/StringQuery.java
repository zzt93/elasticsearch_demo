package cn.superid.search.entities;

/**
 * The query class encapsulate a single string search query
 *
 * @author zzt
 */
public class StringQuery {

  /**
   * Query string to search
   */
  private String query;

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }
}
