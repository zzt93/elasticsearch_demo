package cn.superid.search.impl.entities.time.chat;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.time.chat.ChatIdsQuery;
import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.entities.time.chat.MessagesVO;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface MessagesCustom {

  Page<MessagesPO> findByMessage(ChatQuery chatQuery, Pageable pageable);

  Map<String, PageVO<MessagesVO>> findByMessage(ChatIdsQuery chatQuery);

}
