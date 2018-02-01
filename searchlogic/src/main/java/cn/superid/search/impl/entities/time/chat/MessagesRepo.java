package cn.superid.search.impl.entities.time.chat;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface MessagesRepo extends ElasticsearchRepository<MessagesPO, String>,
    MessagesCustom {


}
