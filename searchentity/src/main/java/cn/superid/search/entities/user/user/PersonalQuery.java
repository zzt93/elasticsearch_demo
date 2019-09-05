package cn.superid.search.entities.user.user;

import cn.superid.search.entities.PagedQuery;
import java.util.Collections;
import java.util.List;
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

  private List<Long> excludes;

  public PersonalQuery() {
  }

  public PersonalQuery(long userId, long roleId, List<Long> excludes, PageRequest pageRequest) {
    this.userId = userId;
    this.roleId = roleId;
    if (excludes != null) {
      this.excludes = excludes;
    } else {
      this.excludes = Collections.emptyList();
    }
    setPageRequest(pageRequest);
  }


}
