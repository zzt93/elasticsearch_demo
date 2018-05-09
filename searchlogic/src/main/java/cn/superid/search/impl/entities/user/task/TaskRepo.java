package cn.superid.search.impl.entities.user.task;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/27.
 */
public interface TaskRepo extends ElasticsearchRepository<TaskPO, String>, TaskCustom {

}
