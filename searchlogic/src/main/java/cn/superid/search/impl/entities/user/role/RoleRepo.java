package cn.superid.search.impl.entities.user.role;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface RoleRepo extends ElasticsearchRepository<RolePO, String>, RoleCustom {

  Page<RolePO> findByTaskIdAndTitle(Long taskId, String title, Pageable pageable);

  Page<RolePO> findByAffairIdAndTitle(Long affairId, String title, Pageable pageable);

  List<RolePO> findByAffairIdAndTitle(Long affairId, String title);

  Page<RolePO> findByTitleAndAffairIdNot(String title, Long affairId, Pageable pageable);
}
