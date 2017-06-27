package com.superid.query.time.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.domain.Slice;

import java.util.Date;

/**
 * Created by zzt on 17/6/5.
 */
public interface ChatRepo extends ElasticsearchRepository<Chat, String> {

    Slice<Chat> findAllBySender(String sender, Pageable pageable);

    Slice<Chat> findAllByReceiver(String receiver, Pageable pageable);

    Slice<Chat> findByMessage(String message, Pageable pageable);

    Slice<Chat> findAllByDateBetween(Date from, Date to, Pageable pageable);
}
