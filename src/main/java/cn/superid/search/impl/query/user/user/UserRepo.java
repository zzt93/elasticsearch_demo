package cn.superid.search.impl.query.user.user;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by zzt on 17/6/5.
 */
// equivalent to extends repo interface
//@RepositoryDefinition()
public interface UserRepo extends ElasticsearchRepository<User, String> {

    Page<User> findByAffairIdAndRealname(Long affairId, String realname, Pageable pageable);

    Page<User> findByAffairIdAndUsername(Long affairId, String username, Pageable pageable);

    Page<User> findByAffairIdAndRole(Long affairId, String role, Pageable pageable);

    Page<User> findByAffairIdAndMainAffair(Long affairId, String mainAffair, Pageable pageable);

    Page<User> findByAffairIdAndTagsIn(Long affairId, List<Tag> tags, Pageable pageable);
}
