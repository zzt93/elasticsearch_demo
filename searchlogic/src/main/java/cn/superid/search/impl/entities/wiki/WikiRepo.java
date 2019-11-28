package cn.superid.search.impl.entities.wiki;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface WikiRepo extends ElasticsearchRepository<Wiki, String>{


  Page<Wiki> findByTitle(String username, Pageable pageable);

}
