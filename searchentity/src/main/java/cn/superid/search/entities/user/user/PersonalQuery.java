package cn.superid.search.entities.user.user;

import cn.superid.search.entities.PagedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PersonalQuery extends PagedQuery {

  private long userId;
  private long roleId;

  public PersonalQuery() {
  }

  public PersonalQuery(long userId, long roleId, PageRequest pageRequest) {
    this.userId = userId;
    this.roleId = roleId;
    setPageRequest(pageRequest);
  }


}
