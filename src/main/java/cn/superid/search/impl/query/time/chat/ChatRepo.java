package cn.superid.search.impl.query.time.chat;

import cn.superid.search.entities.time.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Date;

/**
 * Created by zzt on 17/6/5.
 */
public interface ChatRepo extends ElasticsearchRepository<Chat, String> {

    Page<Chat> findAllBySender(String sender, Pageable pageable);

    Page<Chat> findAllByReceiver(String receiver, Pageable pageable);

    Page<Chat> findByMessage(String message, Pageable pageable);

    Page<Chat> findAllByDateBetween(Date from, Date to, Pageable pageable);
}
