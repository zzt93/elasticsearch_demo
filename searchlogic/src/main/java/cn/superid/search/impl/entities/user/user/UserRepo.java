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
public interface UserRepo extends ElasticsearchRepository<UserPO, String> {


  Page<UserPO> findByUsernameOrSuperId(String username, String superId, Pageable pageable);


  @Query("{\n" +
      "         \"nested\": {\n" +
      "           \"path\": \"tags\",\n" +
      "           \"query\": {\n" +
      "             \"match\": {\n" +
      "               \"tags.des\": \"?0\"\n" +
      "             }}\n" +
      "         }\n" +
      "       }")
  Page<UserPO> findByTagsIn(String query, Pageable pageable);


  @Query(" {" +
      " \"bool\": { " +
      "     \"should\": [\n" +
      "       {\n" +
      "         \"multi_match\": {" +
      "            \"query\":    \"?0\",\n" +
      "            \"fields\":   [ \"username\", \"superId\"]\n" +
      "          }" +
      "       },\n" +
      "       {\n" +
      "         \"nested\": {\n" +
      "           \"path\": \"tags\",\n" +
      "           \"query\": {\n" +
      "             \"match\": {\n" +
      "               \"tags.des\": \"?0\"\n" +
      "             }}\n" +
      "         }\n" +
      "       } " +
      "     ]\n" +
      "  }" +
      "}")
  Page<UserPO> findByUserNameOrSuperIdOrTagsIn(String query, Pageable pageable);
}
