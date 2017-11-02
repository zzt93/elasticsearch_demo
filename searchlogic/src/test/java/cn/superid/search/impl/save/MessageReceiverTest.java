package cn.superid.search.impl.save;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Created by zzt on 17/6/16.
 */
@RunWith(SpringRunner.class)
// Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used,
// and no explicit classes are specified.
@SpringBootTest
public class MessageReceiverTest {


  public static void createIfNotExist(ElasticsearchTemplate esTemplate, Class<?> aClass) {
    if (!esTemplate.indexExists(aClass)) {
      esTemplate.createIndex(aClass);
      esTemplate.putMapping(aClass);
    }
  }

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void process() throws Exception {
  }


}