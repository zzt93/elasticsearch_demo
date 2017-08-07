package cn.superid.search.impl.query.user.user;

import cn.superid.search.entities.Tag;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
// equivalent to extends repo interface
//@RepositoryDefinition()
public interface UserRepo extends ElasticsearchRepository<UserPO, String> {

  Page<UserPO> findByAffairIdAndRealname(Long affairId, String realname, Pageable pageable);

  Page<UserPO> findByAffairIdAndUsername(Long affairId, String username, Pageable pageable);

  Page<UserPO> findByAffairIdAndRole(Long affairId, String role, Pageable pageable);

  Page<UserPO> findByAffairIdAndMainAffair(Long affairId, String mainAffair, Pageable pageable);

  Page<UserPO> findByAffairIdAndTagsIn(Long affairId, List<Tag> tags, Pageable pageable);
}
