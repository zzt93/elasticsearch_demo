package cn.superid.search.impl.entities.time.chat;

import static org.junit.Assert.assertEquals;

import cn.superid.search.entities.time.chat.ChatIdsQuery;
import com.google.common.collect.Lists;
import java.util.Map;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MessagesRepoImplTest {

  @Autowired
  private MessagesRepo messagesRepo;

  @Test
  public void countMessage() {
    String has = "0ABYB@GGrZB!EGrZB";
    Map<String, Long> test = messagesRepo
        .countMessage(new ChatIdsQuery("1", Lists.newArrayList("B2lz$", has), null, PageRequest.of(0, 10)));

    assertEquals(1, test.size());
    assertEquals(32L, (long) test.getOrDefault(has, 0L));
  }
}