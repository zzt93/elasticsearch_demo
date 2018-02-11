package cn.superid.search.impl.entities.user.affair;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface AffairCustom {

  Page<AffairPO> findAny(String info, Pageable pageable);


}
