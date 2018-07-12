package cn.superid.search.impl.entities;

import cn.superid.search.entities.user.task.TaskQuery;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

/**
 * @author zzt
 */
public class JsonTest {

  @Test
  public void toJson() throws Exception {
    TaskQuery taskQuery = new TaskQuery();
    taskQuery.setPageRequest(PageRequest.of(0,10, Sort.by(Order.asc("offTime").nullsLast())));
    taskQuery.setQuery("as");
    taskQuery.setRoles(Lists.newArrayList(1L));
    taskQuery.setState((byte) 1);
    String jackson = new ObjectMapper().writeValueAsString(taskQuery);
    System.out.println(jackson);
    Assert.assertTrue(!jackson.contains("orders"));
    String gson = new Gson().toJson(taskQuery);
    Assert.assertTrue(gson.contains("orders"));
  }
}
