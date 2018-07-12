package cn.superid.search.impl.entities.user.task;

import cn.superid.search.entities.user.task.TaskQuery;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * @author zzt
 */
public class TaskRepoImplTest {

  @Before
  public void setUp() throws Exception {
    TaskQuery taskQuery = new TaskQuery();
    taskQuery.setQuery("asdf");
    taskQuery.setRoles(Lists.newArrayList(1L));
    taskQuery.setPageRequest(PageRequest.of(0, 10, Sort.by(Order.asc("offTime"))));
  }

  @Test
  public void findByAll() throws Exception {
  }

}