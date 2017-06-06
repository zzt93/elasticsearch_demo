package com.superid.query.chat;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

/**
 * Created by zzt on 17/6/5.
 */
public interface ChatRepo extends ElasticsearchRepository<Chat, String> {

    List<Chat> findAllBySender(String sender);
    List<Chat> findBySender(String sender);

    List<Chat> findAllByReceiver(String receiver);

    List<Chat> findByMessage(String message);
}
