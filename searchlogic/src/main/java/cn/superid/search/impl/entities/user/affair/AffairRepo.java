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

  Page<AffairPO> findByNameAndPublicType(String name, Byte publicType, Pageable pageable);

  Page<AffairPO> findBySuperIdAndPublicType(String superId, Byte publicType, Pageable pageable);

  Page<AffairPO> findByTagsInAndPublicType(String[] tags, Byte publicType, Pageable pageable);

}
