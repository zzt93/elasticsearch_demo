package cn.superid.search.impl.query.time.announcement;

import cn.superid.search.entities.time.Announcement;
import com.google.common.collect.Lists;
import com.google.common.io.ByteStreams;
import java.io.IOException;
import java.io.InputStream;
import org.junit.Before;
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
public class AnnouncementRepoImplTest {
  private Long affairId = 1L;

  @Autowired
  private AnnouncementRepo announcementRepo;

  private static String readAll(String title) throws IOException {
    InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream(title);
    return new String(ByteStreams.toByteArray(resourceAsStream), "utf8");
  }

  @Before
  public void setUp() throws Exception {
    String modifierUser = "xxx";
    String modifierRole = "cto";

    String t1 = "java开发规范";
    announcementRepo.save(
        new Announcement("a", t1, readAll(t1), Lists.newArrayList(), modifierRole, modifierUser, affairId));
    String t2 = "我的第一个JAVA程序";
    announcementRepo.save(
        new Announcement("b", t2, readAll(t2), Lists.newArrayList(), modifierRole, modifierUser, affairId));
    String t3 = "Java 基础语法";
    announcementRepo.save(
        new Announcement("c", t3, readAll(t3), Lists.newArrayList(), modifierRole, modifierUser, affairId));
  }

  @Test
  public void findByTitleOrModifierRoleOrModifierUserOrTagsIn() throws Exception {
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("java", affairId, new PageRequest(0, 10))
        .getContent());
  }

}