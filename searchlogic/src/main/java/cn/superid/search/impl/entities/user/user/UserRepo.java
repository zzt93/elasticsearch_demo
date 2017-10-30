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


  @Query("{\n"
      + "    \"bool\": {\n"
      + "      \"should\": [\n"
      + "        {\n"
      + "          \"prefix\" : { \"username\" : \"?0\" }\n"
      + "        },\n"
      + "        {\n"
      + "          \"prefix\" : { \"email\" : \"?0\" }\n"
      + "        },\n"
      + "        {\n"
      + "          \"prefix\" : { \"mobile\" : \"?0\" }\n"
      + "        },\n"
      + "        {\n"
      + "          \"prefix\" : { \"superId\" : \"?0\" }\n"
      + "        },\n"
      + "        {\n"
      + "          \"nested\": {\n"
      + "            \"path\": \"tags\",\n"
      + "            \"query\": {\n"
      + "              \"match\": {\n"
      + "                \"tags.des\": \"?0\"\n"
      + "              }\n"
      + "            }\n"
      + "          }\n"
      + "        }\n"
      + "      ]\n"
      + "    }\n"
      + "  }")
  Page<UserPO> findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(String query, Pageable pageable);
}
