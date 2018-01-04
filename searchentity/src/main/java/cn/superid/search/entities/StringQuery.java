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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    StringQuery that = (StringQuery) o;

    return query != null ? query.equals(that.query) : that.query == null;
  }

  @Override
  public int hashCode() {
    return query != null ? query.hashCode() : 0;
  }

  @Override
  public String toString() {
    return "StringQuery{" +
        "query='" + query + '\'' +
        '}';
  }
}
