package cn.superid.search.entities.user.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentQuery extends PersonalQuery {

  public StudentQuery() {
  }

  public StudentQuery(long userId, long roleId, PageRequest pageRequest) {
    super(userId, roleId, pageRequest);
  }
}
