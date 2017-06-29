package com.superid.save;

import com.superid.MessageFormatReceiver;
import com.superid.query.time.announcement.AnnouncementRepo;
import com.superid.query.time.chat.ChatRepo;
import com.superid.query.time.task.TaskRepo;
import com.superid.query.user.file.FileRepo;
import com.superid.query.user.role.RoleRepo;
import com.superid.query.user.user.UserRepo;
import com.superid.query.user.warehouse.MaterialRepo;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

/**
 * Created by zzt on 17/6/7.
 */
@Configuration
public class RabbitConfig {

    @Value("${search.rabbitmq.queue}")
    private String queueName;
    @Value("${search.rabbitmq.exchange}")
    private String exchangeName;
    @Value("${search.rabbitmq.binding-key}")
    private String bindingKey;

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

    @Bean
    TopicExchange exchange() {
        return new TopicExchange(exchangeName, true, false);
    }

    @Bean
    Binding binding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(bindingKey);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageFormatReceiver receiver) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(receiver);
        return container;
    }

    @Bean
    MessageFormatReceiver receiver(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, RoleRepo roleRepo, AnnouncementRepo announcementRepo, TaskRepo taskRepo, MaterialRepo materialRepo, ElasticsearchTemplate esTemplate) {
        return new SaveReceiver(userRepo, chatRepo, fileRepo, roleRepo, announcementRepo, taskRepo, materialRepo, esTemplate);
    }
}
