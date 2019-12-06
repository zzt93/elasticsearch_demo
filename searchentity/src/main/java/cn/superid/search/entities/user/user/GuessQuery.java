package cn.superid.search.entities.user.user;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class GuessQuery extends PersonalQuery {

  public GuessQuery() {
  }

  public GuessQuery(long userId, long roleId, List<Long> excludes,
      PageRequest pageRequest) {
    super(userId, roleId, excludes, pageRequest);
  }
}