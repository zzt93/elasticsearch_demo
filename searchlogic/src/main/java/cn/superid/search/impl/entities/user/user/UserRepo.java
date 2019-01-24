package cn.superid.search.impl.entities.user.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
// equivalent to extends repo interface
//@RepositoryDefinition()
public interface UserRepo extends ElasticsearchRepository<UserPO, String>, UserCustom {


  Page<UserPO> findByUsernameOrSuperId(String username, String superId, Pageable pageable);


  @Query("{\n"
      + "    \"term\": {\"tags\": \"?0\"}\n"
      + "  }")
  Page<UserPO> findByTagsIn(String query, Pageable pageable);

  UserPO findByMobile(String query);

}
