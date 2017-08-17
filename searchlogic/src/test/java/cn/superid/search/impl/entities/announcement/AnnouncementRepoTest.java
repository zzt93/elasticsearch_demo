package cn.superid.search.impl.entities.announcement;

import cn.superid.search.entities.Tag;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.collect.Lists;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zzt on 17/6/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementRepoTest {

  private static Long affairId = 1L;
  private static final List<Long> affairIds = Lists.newArrayList(affairId);


  private static Tag t1 = new Tag("t1");
  private static Tag t2 = new Tag("t2");
  private static Tag t3 = new Tag("t3");
  @Autowired
  private AnnouncementRepo announcementRepo;
  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;

  @Before
  public void save() {
    MessageReceiverTest.createIfNotExist(esTemplate, AnnouncementPO.class);

    String role1 = "role1";
    String role2 = "role2";
    String content = "this is a announcement";
    AnnouncementPO a1 = new AnnouncementPO("1", "announcement1", content,
        Lists.newArrayList(t1, t2),
        role1, role2, affairId);

    String role3 = "role3";
    AnnouncementPO a2 = new AnnouncementPO("2", "announcement2", content,
        Lists.newArrayList(t1, t3),
        role1, role3, affairId);

    AnnouncementPO a3 = new AnnouncementPO("3", "announcement3", content,
        Lists.newArrayList(t2, t3),
        role2, role3, affairId);

    announcementRepo.save(a1);
    announcementRepo.save(a2);
    announcementRepo.save(a3);

    String role4 = "后端开发";
    String role5 = "前端开发";
    String role6 = "前端架构";
    String role7 = "后端架构";
    announcementRepo
        .save(
            new AnnouncementPO("4", "后端开发技术", content, Lists.newArrayList(), role4, role4,
                affairId));
    announcementRepo
        .save(
            new AnnouncementPO("5", "前端开发技术", content, Lists.newArrayList(), role5, role5,
                affairId));
    announcementRepo
        .save(
            new AnnouncementPO("6", "前端人员", content, Lists.newArrayList(), role6, role6, affairId));
    announcementRepo
        .save(
            new AnnouncementPO("7", "后端人员", content, Lists.newArrayList(), role7, role7, affairId));

    announcementRepo.save(
        new AnnouncementPO("8", "Brown fox brown dog", content, Lists.newArrayList(), role1, role1,
            affairId));
    announcementRepo.save(
        new AnnouncementPO("9", "The quick brown fox jumps over the lazy dog", content,
            Lists.newArrayList(), role1, role1, affairId));
    announcementRepo.save(
        new AnnouncementPO("10", "The quick brown fox jumps over the quick dog", content,
            Lists.newArrayList(), role1, role1, affairId));
    announcementRepo.save(
        new AnnouncementPO("11", "The quick brown fox", content, Lists.newArrayList(), role1, role1,
            affairId));
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
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "t1",
            new PageRequest(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "t2", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "t2",
            new PageRequest(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "t3", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "t3",
            new PageRequest(0, 10))
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
//        System.out.println(announcementRepo.findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair("announcement", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair("announcementx", "", "", Lists.newArrayList(t1, t2), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair("announcement role1", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());

    System.out.println(
        announcementRepo.findByAll("_all", "announcement2 role1", new PageRequest(0, 10))
            .getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds,
            "announcement2 role1",
            new PageRequest(0, 10)).getContent());
  }

  @Test
  public void testChinese() {
    System.out
        .println(announcementRepo.findByAll("_all", "后端", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "后端",
            new PageRequest(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "开发", new PageRequest(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "开发",
            new PageRequest(0, 10))
        .getContent());
  }

  @Test
  public void testMultiWord() {
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds,
            "BROWN DOG", new PageRequest(0, 10))
        .getContent());
  }
}