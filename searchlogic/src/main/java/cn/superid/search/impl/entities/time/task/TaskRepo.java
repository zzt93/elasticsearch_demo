package cn.superid.search.impl.entities.time.task;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/27.
 */
public interface TaskRepo extends ElasticsearchRepository<TaskPO, String> {

  Page<TaskPO> findByTitle(String title, Pageable page);
}
