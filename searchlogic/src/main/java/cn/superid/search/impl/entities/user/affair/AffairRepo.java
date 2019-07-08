package cn.superid.search.impl.entities.user.affair;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/29.
 */
public interface AffairRepo extends ElasticsearchRepository<AffairPO, String>, AffairCustom {

  Optional<AffairPO> findById(String id);

  Page<AffairPO> findByName(String name, Pageable pageable);

  Page<AffairPO> findByNameAndAllianceId(String name, Long allianceId, Pageable pageable);

  Page<AffairPO> findBySuperId(String superId, Pageable pageable);

  Page<AffairPO> findByTagsIn(String[] tags, Pageable pageable);

}
