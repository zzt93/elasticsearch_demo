package com.superid.query.precreate.user;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by zzt on 17/6/5.
 */
// equivalent to extends repo interface
//@RepositoryDefinition()
public interface UserRepo extends ElasticsearchRepository<User, String> {

    User findById(String id);

    List<User> findByRealname(String realname);

    List<User> findByUsername(String username);

}
