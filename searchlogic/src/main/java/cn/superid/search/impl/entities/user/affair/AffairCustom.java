package cn.superid.search.impl.entities.user.affair;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface AffairCustom {

  Page<AffairPO> findAny(String info, Pageable pageable);

  List<AffairPO> findAlliance(String query, Long allianceId, Pageable pageable);

}
