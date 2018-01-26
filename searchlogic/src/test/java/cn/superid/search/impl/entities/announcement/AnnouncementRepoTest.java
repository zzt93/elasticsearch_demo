package cn.superid.search.impl.entities.announcement;

import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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

  private static final Timestamp modifyTime;
  private static final Long affairId = 9038L;
  private static final List<Long> affairIds = Lists.newArrayList(affairId);
  private static final String T1 = "T1";
  private static final String T2 = "T2";
  private static final String T3 = "T3";

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
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;

  @Before
  public void save() throws JsonProcessingException {
    suffix.setSuffix("2016.10");
    MessageReceiverTest.createIfNotExist(esTemplate, AnnouncementPO.class);

    String role1 = "role1";
    String role2 = "role2";
    String content = "this is a announcement";
    AnnouncementPO a1 = new AnnouncementPO("11", "announcement1", content,
        new String[]{T1, T2},
        role1, role2, affairId, modifyTime);

    String role3 = "role3";
    AnnouncementPO a2 = new AnnouncementPO("12", "announcement2", content,
        new String[]{T1, T3},
        role1, role3, affairId, modifyTime);

    AnnouncementPO a3 = new AnnouncementPO("13", "announcement3", content,
        new String[]{T2, T3},
        role2, role3, affairId, modifyTime);

    announcementRepo.save(a1);
    announcementRepo.save(a2);
    announcementRepo.save(a3);

    String role4 = "后端开发";
    String role5 = "前端开发";
    String role6 = "前端架构";
    String role7 = "后端架构";
    announcementRepo
        .save(
            new AnnouncementPO("14", "后端开发技术", content, new String[]{}, role4, role4,
                affairId, modifyTime));
    announcementRepo
        .save(
            new AnnouncementPO("15", "前端开发技术", content, new String[]{}, role5, role5,
                affairId, modifyTime));
    announcementRepo
        .save(
            new AnnouncementPO("16", "前端人员", content, new String[]{}, role6, role6, affairId,
                modifyTime));
    announcementRepo
        .save(
            new AnnouncementPO("17", "后端人员", content, new String[]{}, role7, role7, affairId,
                modifyTime));

    announcementRepo.save(
        new AnnouncementPO("18", "Brown fox brown dog", content, new String[]{}, role1, role1,
            affairId, modifyTime));
    announcementRepo.save(
        new AnnouncementPO("19", "The quick brown fox jumps over the lazy dog", content,
            new String[]{}, role1, role1, affairId, modifyTime));
    announcementRepo.save(
        new AnnouncementPO("110", "The quick brown fox jumps over the quick dog", content,
            new String[]{}, role1, role1, affairId, modifyTime));
    announcementRepo.save(
        new AnnouncementPO("111", "The quick brown fox", content, new String[]{}, role1, role1,
            affairId, modifyTime));
  }

  @Test
  public void findAll() {
    announcementRepo.findAll().forEach(System.out::println);
    System.out
        .println(announcementRepo.findByAll("_all", "T1", PageRequest.of(0, 10)).getContent());
  }


  @Test
  public void findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair()
      throws Exception {
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "T1",
            PageRequest.of(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "T2", PageRequest.of(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "T2",
            PageRequest.of(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "T3", PageRequest.of(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "T3",
            PageRequest.of(0, 10))
        .getContent());

//        TagPO t10 = new TagPO("10");
//        TagPO t20 = new TagPO("20");
//        Slice<Announcement> test1 = announcementRepo.findByTitle("announcement", PageRequest.of(0, 10));
//        System.out.println(test1.getContent());
//        List<Announcement> test2 = announcementRepo.findAllByTitle("announcement1", PageRequest.of(0, 10));
//        System.out.println(test2);
//        System.out.println(announcementRepo.findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair("announcement", "", "", new String[]{t10, t20}, PageRequest.of(0, 10)).getContent());
//        System.out.println(announcementRepo.findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair("announcementx", "", "", new String[]{T1, T2}, PageRequest.of(0, 10)).getContent());
//        System.out.println(announcementRepo.findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair("announcement role1", "", "", new String[]{t10, t20}, PageRequest.of(0, 10)).getContent());

    System.out.println(
        announcementRepo.findByAll("_all", "announcement2 role1", PageRequest.of(0, 10))
            .getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds,
            "announcement2 role1",
            PageRequest.of(0, 10)).getContent());
  }

  @Test
  public void testChinese() {
    System.out
        .println(announcementRepo.findByAll("_all", "后端", PageRequest.of(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "后端",
            PageRequest.of(0, 10))
        .getContent());
    System.out
        .println(announcementRepo.findByAll("_all", "开发", PageRequest.of(0, 10)).getContent());
    System.out.println(announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds, "开发",
            PageRequest.of(0, 10))
        .getContent());
  }

  @Test
  public void testMultiWord() {
    List<AnnouncementPO> brown_dog = announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(affairIds,
            "BROWN DOG", PageRequest.of(0, 10))
        .getContent();
    System.out.println(brown_dog);
  }
}