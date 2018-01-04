package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PagedQuery;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 * @see PagedQuery
 */
public class AffairQuery extends PagedQuery {

  public AffairQuery() {
  }

  public AffairQuery(String query, PageRequest pageRequest) {
    setQuery(query);
    setPageRequest(pageRequest);
  }

  @Override
  public String toString() {
    return "AffairQuery{"
        + super.toString()
        + "}";
  }
}
