package cn.superid.search.impl.entities.user.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface UserCustom {

  Page<UserPO> findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(String query, Pageable pageable);

}
