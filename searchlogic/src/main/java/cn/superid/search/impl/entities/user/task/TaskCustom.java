package cn.superid.search.impl.entities.user.task;

import cn.superid.search.entities.user.task.TaskQuery;
import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface TaskCustom {

  Page<TaskPO> findByAll(TaskQuery taskQuery);

}
