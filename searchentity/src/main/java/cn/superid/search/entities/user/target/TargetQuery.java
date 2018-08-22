package cn.superid.search.entities.user.target;

import cn.superid.search.entities.StringQuery;
import java.util.List;

/**
 * @author zzt
 */
public class TargetQuery extends StringQuery {

  private List<Long> affairs;

  public TargetQuery() {
  }

  public TargetQuery(List<Long> affairs) {

    this.affairs = affairs;
  }

  public List<Long> getAffairs() {
    return affairs;
  }

  public void setAffairs(List<Long> affairs) {
    this.affairs = affairs;
  }
}
