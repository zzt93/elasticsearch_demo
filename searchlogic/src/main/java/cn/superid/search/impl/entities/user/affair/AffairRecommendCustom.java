package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.user.user.GuessQuery;
import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface AffairRecommendCustom {

  Page<AffairPO> recommend(GuessQuery info);

}
