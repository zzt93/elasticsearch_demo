package cn.superid.search.impl.entities.time.chat;

import java.util.Date;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface ChatRepo extends ElasticsearchRepository<ChatPO, String> {

  Page<ChatPO> findAllBySender(String sender, Pageable pageable);

  Page<ChatPO> findAllByReceiver(String receiver, Pageable pageable);

  Page<ChatPO> findByMessage(String message, Pageable pageable);

  Page<ChatPO> findAllByDateBetween(Date from, Date to, Pageable pageable);
}
