package cn.superid.search.impl.query.time.task;

import cn.superid.search.entities.time.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/27.
 */
public interface TaskRepo extends ElasticsearchRepository<Task, String> {

    Page<Task> findByTitle(String title, Pageable page);
}
