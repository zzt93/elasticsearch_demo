package cn.superid.search.impl.entities.announcement;

import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementRepoImplTest {

  private static final Timestamp modifyTime;
  private static final Long affairId = 1L;

  static {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss.SSS");
    Date parsedDate = null;
    try {
      parsedDate = dateFormat.parse("2016.10.11 10:10:10.100");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    modifyTime = new Timestamp(parsedDate.getTime());
  }

  @Autowired
  private AnnouncementRepo announcementRepo;
  @Autowired
  private ElasticsearchTemplate esTemplate;
  @Autowired
  private Suffix suffix;

  private static String readAll(String title) throws IOException {
    InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(title);
    return new String(ByteStreams.toByteArray(resourceAsStream), "utf8");
  }

  @Before
  public void setUp() throws Exception {
    suffix.setSuffix("2016-09");
    MessageReceiverTest.createIfNotExist(esTemplate, AnnouncementPO.class);

    String modifierUser = "xxx";
    String modifierRole = "cto";

    String t1 = "java开发规范";
    announcementRepo.save(
        new AnnouncementPO("a", t1, readAll(t1), Lists.newArrayList(), modifierRole, modifierUser,
            affairId, modifyTime));
    String t2 = "我的第一个JAVA程序";
    announcementRepo.save(
        new AnnouncementPO("b", t2, readAll(t2), Lists.newArrayList(), modifierRole, modifierUser,
            affairId, modifyTime));
    String t3 = "Java 基础语法";
    announcementRepo.save(
        new AnnouncementPO("c", t3, readAll(t3), Lists.newArrayList(), modifierRole, modifierUser,
            affairId, modifyTime));
  }

  @Test
  public void findByTitleOrModifierRoleOrModifierUserOrTagsIn() throws Exception {
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(
            Lists.newArrayList(affairId), "java", PageRequest.of(0, 10))
        .getContent());
  }

}