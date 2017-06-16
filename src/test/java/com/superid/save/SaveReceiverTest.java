package com.superid.save;

import com.superid.MessageFormat;
import com.superid.MessageFormatSender;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zzt on 17/6/16.
 */
@RunWith(SpringRunner.class)
// Automatically searches for a @SpringBootConfiguration when nested @Configuration is not used,
// and no explicit classes are specified.
@SpringBootTest
public class SaveReceiverTest {

    @Autowired
    private MessageFormatSender sender;

    @Test
    public void receiveMessage() throws Exception {
        for (int i = 0; i < 10; i++) {
            System.out.printf("Sending %dth message...\n", i);
            sender.send(new MessageFormat().addPayload("test", "value"));
        }
    }


    @TestPropertySource("")
    @Configuration
    static class ContextConfiguration {


        @Bean
        MessageFormatSender sender(RabbitTemplate rabbitTemplate) {
            return new MessageFormatSender(rabbitTemplate);
        }
    }

}