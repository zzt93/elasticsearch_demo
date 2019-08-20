package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.user.affair.AffairQuery;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface AffairCustom {

  Page<AffairPO> findAny(String info, Byte mold, Pageable pageable);

  Page<AffairPO> findAny(AffairQuery affairQuery);

  List<AffairPO> findAlliance(String query, Long allianceId, Pageable pageable);

  Page<AffairPO> findByNameAndAllianceId(String name, Long allianceId, Pageable pageable);

}
