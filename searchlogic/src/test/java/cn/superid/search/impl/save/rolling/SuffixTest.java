package cn.superid.search.impl.save.rolling;

import cn.superid.search.impl.entities.time.chat.MessagesPO;
import cn.superid.search.impl.query.rolling.Suffix;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author zzt
 */
public class SuffixTest {

  @Test
  public void indexName() throws Exception {
    String s = Suffix.indexName(MessagesPO.class, "");
    Assert.assertEquals(s, "messages");
  }

  @Test
  public void timeBasedPattern() throws Exception {
//    String s = Suffix.timeBasedPattern(MessagesPO.class, 0, 0);
//    Assert.assertEquals(s, "messages-*");
//    String s1 = Suffix.timeBasedPattern(MessagesPO.class, 1507425461*1000L, 1517465461*1000L);
//    Assert.assertEquals(s1, "messages-201*");
//    String s2 = Suffix.timeBasedPattern(MessagesPO.class, 1517462461*1000L, 1517465461*1000L);
//    Assert.assertEquals(s2, "messages-2018.02*");
  }

}