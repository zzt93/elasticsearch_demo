package cn.superid.search.impl.entities.time.chat;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface MessagesRepo extends ElasticsearchRepository<MessagesPO, String>,
    MessagesCustom {

  Page<MessagesPO> findAllBySender(String sender, Pageable pageable);

  Page<MessagesPO> findAllByReceiver(String receiver, Pageable pageable);

}
