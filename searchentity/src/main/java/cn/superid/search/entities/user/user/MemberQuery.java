package cn.superid.search.entities.user.user;

import cn.superid.search.entities.StringQuery;
import java.util.List;

/**
 * @author zzt
 */
public class MemberQuery extends StringQuery {

  private List<Long> affairIds;

  public List<Long> getAffairIds() {
    return affairIds;
  }

  public void setAffairIds(List<Long> affairIds) {
    this.affairIds = affairIds;
  }
}
