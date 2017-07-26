package cn.superid.search.impl.save;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

//import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * Created by zzt on 17/6/16.
 */
@RunWith(SpringRunner.class)
// Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used,
// and no explicit classes are specified.
@SpringBootTest
public class SaveReceiverTest {

  @Autowired
//    private MessageFormatSender sender;

  @Test
  public void receiveMessage() throws Exception {
    for (int i = 0; i < 10; i++) {
      System.out.printf("Sending %dth message...\n", i);
//            sender.send("search.test.save", new MessageFormat().addPayload("test", "value" + i));
    }
  }


  @Configuration
  static class ContextConfiguration {

//        @Bean
//        RabbitTemplate rabbitTemplate() {
//            CachingConnectionFactory cf = new CachingConnectionFactory("192.168.1.100", 5672);
//            cf.setUsername("searcher");
//            cf.setPassword("searcher");
//            cf.setVirtualHost("search_host");
//            RabbitTemplate rabbitTemplate = new RabbitTemplate(cf);
//            rabbitTemplate.setExchange("search-exchange");
//            return rabbitTemplate;
//        }

//        @Bean
//        MessageFormatSender sender(RabbitTemplate rabbitTemplate) {
//            return new MessageFormatSender(rabbitTemplate);
//        }
  }

}