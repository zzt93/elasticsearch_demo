package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.Tag;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
// equivalent to extends repo interface
//@RepositoryDefinition()
public interface UserRepo extends ElasticsearchRepository<UserPO, String>, UserCustom {

  List<UserPO> findTop20ByTagsIn(List<Tag> tags);

  List<UserPO> findTop20ByUsernameOrSuperId(String username, String superId);


}
