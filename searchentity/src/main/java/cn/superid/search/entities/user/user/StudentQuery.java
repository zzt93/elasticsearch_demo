package cn.superid.search.entities.user.user;

import cn.superid.search.entities.PagedQuery;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQuery extends PagedQuery {

  private long userId;

}
