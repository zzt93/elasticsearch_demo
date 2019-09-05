package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.user.user.GuessQuery;
import cn.superid.search.entities.user.user.InterestQuery;
import cn.superid.search.entities.user.user.StudentQuery;
import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface PersonalRecommendCustom {

  Page<UserPO> similarTag(InterestQuery query);

  Page<UserPO> similarStudent(StudentQuery query);

  Page<UserPO> random(GuessQuery query);
}
