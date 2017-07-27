package cn.superid.search.impl.query.time.announcement;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.time.Announcement;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zzt on 17/6/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementRepoTest {
  private static Long affairId = 1L;

  private static Tag t1 = new Tag("t1");
  private static Tag t2 = new Tag("t2");
  private static Tag t3 = new Tag("t3");
  @Autowired
  private AnnouncementRepo announcementRepo;

  @Before
  public void save() {
    String role1 = "role1";
    String role2 = "role2";
    String content = "this is a announcement";
    Announcement a1 = new Announcement("1", "announcement1", content, Lists.newArrayList(t1, t2),
        role1, role2, affairId);

    String role3 = "role3";
    Announcement a2 = new Announcement("2", "announcement2", content, Lists.newArrayList(t1, t3),
        role1, role3, affairId);

    Announcement a3 = new Announcement("3", "announcement3", content, Lists.newArrayList(t2, t3),
        role2, role3, affairId);

    announcementRepo.save(a1);
    announcementRepo.save(a2);
    announcementRepo.save(a3);

    String role4 = "后端开发";
    String role5 = "前端开发";
    String role6 = "前端架构";
    String role7 = "后端架构";
    announcementRepo
        .save(new Announcement("4", "后端开发技术", content, Lists.newArrayList(), role4, role4, affairId));
    announcementRepo
        .save(new Announcement("5", "前端开发技术", content, Lists.newArrayList(), role5, role5, affairId));
    announcementRepo
        .save(new Announcement("6", "前端人员", content, Lists.newArrayList(), role6, role6, affairId));
    announcementRepo
        .save(new Announcement("7", "后端人员", content, Lists.newArrayList(), role7, role7, affairId));

    announcementRepo.save(
        new Announcement("8", "Brown fox brown dog", content, Lists.newArrayList(), role1, role1, affairId));
    announcementRepo.save(
        new Announcement("9", "The quick brown fox jumps over the lazy dog", content,
            Lists.newArrayList(), role1, role1, affairId));
    announcementRepo.save(
        new Announcement("10", "The quick brown fox jumps over the quick dog", content,
            Lists.newArrayList(), role1, role1, affairId));
    announcementRepo.save(
        new Announcement("11", "The quick brown fox", content, Lists.newArrayList(), role1, role1, affairId));
  }

  @Test
  public void findAll() {
    announcementRepo.findAll().forEach(System.out::println);
  }

  @Test
  public void findTagsInList() {
    System.out
        .println(announcementRepo.findByAll("_all", "t1", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("t1", affairId, new PageRequest(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "t2", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("t2", affairId, new PageRequest(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "t3", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("t3", affairId, new PageRequest(0, 10))
        .getContent());
  }

  @Test
  public void findAllByTitleOrModifierRoleOrModifierUserOrTagsIn() throws Exception {
//        Tag t10 = new Tag("10");
//        Tag t20 = new Tag("20");
//        Slice<Announcement> test1 = announcementRepo.findByTitle("announcement", new PageRequest(0, 10));
//        System.out.println(test1.getContent());
//        List<Announcement> test2 = announcementRepo.findAllByTitle("announcement1", new PageRequest(0, 10));
//        System.out.println(test2);
//        System.out.println(announcementRepo.findByTitleOrModifierRoleOrModifierUserOrTagsIn("announcement", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findByTitleOrModifierRoleOrModifierUserOrTagsIn("announcementx", "", "", Lists.newArrayList(t1, t2), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findByTitleOrModifierRoleOrModifierUserOrTagsIn("announcement role1", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());

    System.out.println(
        announcementRepo.findByAll("_all", "announcement2 role1", new PageRequest(0, 10))
            .getContent());
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("announcement2 role1", affairId,
            new PageRequest(0, 10)).getContent());
  }

  @Test
  public void testChinese() {
    System.out
        .println(announcementRepo.findByAll("_all", "后端", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("后端", affairId, new PageRequest(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "开发", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("开发", affairId, new PageRequest(0, 10))
        .getContent());
  }

  @Test
  public void testMultiWord() {
    System.out.println(announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn("BROWN DOG", affairId, new PageRequest(0, 10))
        .getContent());
  }
}