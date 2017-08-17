package cn.superid.search.impl.save;

import cn.superid.common.notification.dto.NotificationMessage;
import cn.superid.common.notification.enums.NotificationType;
import com.google.common.collect.Maps;
import java.util.HashMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.integration.support.MutableMessage;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Created by zzt on 17/6/16.
 */
@RunWith(SpringRunner.class)
// Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used,
// and no explicit classes are specified.
@SpringBootTest
public class MessageReceiverTest {

  public static void save(MessageReceiver messageReceiver, Object data,
      NotificationType notificationType) {
    NotificationMessage payload = new NotificationMessage();
    payload.setType(notificationType);
    HashMap<Object, Object> param = Maps.newHashMap();
    param.put("data", data);
    param.put("verb", RequestMethod.PUT);
    payload.setParam(param);
    Message<NotificationMessage> message = new MutableMessage<>(payload);
    messageReceiver.process(message);
  }

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