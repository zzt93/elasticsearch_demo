package cn.superid.search.impl.entities.time.chat;

import cn.superid.search.entities.time.chat.ChatQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface MessagesCustom {

  Page<MessagesPO> findByMessage(ChatQuery chatQuery, Pageable pageable);

}
