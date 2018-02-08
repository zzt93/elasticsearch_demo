package cn.superid.search.impl.entities.announcement;

import static org.junit.Assert.assertEquals;

import cn.superid.search.entities.time.announcement.AnnouncementQuery;
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
import org.junit.After;
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
  private static final long ROLE_ID1 = 1L;


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
    long[] roles = new long[]{ROLE_ID1, 2L};

    AnnouncementPO a1 = new AnnouncementPO("11", "announcement1", content,
        new String[]{T1, T2},
        roles, role2, affairId, modifyTime);

    String role3 = "role3";
    AnnouncementPO a2 = new AnnouncementPO("12", "announcement2", content,
        new String[]{T1, T3},
        roles, role3, affairId, modifyTime);

    AnnouncementPO a3 = new AnnouncementPO("13", "announcement3", content,
        new String[]{T2, T3},
        roles, role3, affairId, modifyTime);

    announcementRepo.save(a1);
    announcementRepo.save(a2);
    announcementRepo.save(a3);

    String role4 = "后端开发";
    String role5 = "前端开发";
    String role6 = "前端架构";
    String role7 = "后端架构";
    announcementRepo
        .save(
            new AnnouncementPO("14", "后端开发技术", content, new String[]{}, roles, role4,
                affairId, modifyTime));
    announcementRepo
        .save(
            new AnnouncementPO("15", "前端开发技术", content, new String[]{}, roles, role5,
                affairId, modifyTime));
    announcementRepo
        .save(
            new AnnouncementPO("16", "前端人员", content, new String[]{}, roles, role6, affairId,
                modifyTime));
    announcementRepo
        .save(
            new AnnouncementPO("17", "后端人员", content, new String[]{}, roles, role7, affairId,
                modifyTime));

    announcementRepo.save(
        new AnnouncementPO("18", "Brown fox brown dog", content, new String[]{}, roles, role1,
            affairId, modifyTime));
    announcementRepo.save(
        new AnnouncementPO("19", "The quick brown fox jumps over the lazy dog", content,
            new String[]{}, roles, role1, affairId, modifyTime));
    announcementRepo.save(
        new AnnouncementPO("110", "The quick brown fox jumps over the quick dog", content,
            new String[]{}, roles, role1, affairId, modifyTime));
    announcementRepo.save(
        new AnnouncementPO("111", "The quick brown fox", content, new String[]{}, roles, role1,
            affairId, modifyTime));
  }

  @Test
  public void findAll() {
    announcementRepo.findAll().forEach(System.out::println);
    List<AnnouncementPO> content = announcementRepo.findByAll("_all", "T1", PageRequest.of(0, 10))
        .getContent();
    assertEquals(content.size(), 2);
  }


  @Test
  public void findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair()
      throws Exception {
    List<AnnouncementPO> t1 = announcementRepo
        .findByTitleOrContentOrTags(new AnnouncementQuery(affairIds, "T1", null,
                Lists.newArrayList(ROLE_ID1)),
            PageRequest.of(0, 10))
        .getContent();
    assertEquals(t1.size(), 2);
    List<AnnouncementPO> t2 = announcementRepo.findByAll("_all", "T2", PageRequest.of(0, 10))
        .getContent();
    assertEquals(t2.size(), 2);
    List<AnnouncementPO> t21 = announcementRepo
        .findByTitleOrContentOrTags(new AnnouncementQuery(affairIds, "T2", null,
                Lists.newArrayList(ROLE_ID1)),
            PageRequest.of(0, 10))
        .getContent();
    assertEquals(t21.size(), 2);
    List<AnnouncementPO> t3 = announcementRepo.findByAll("_all", "T3", PageRequest.of(0, 10))
        .getContent();
    assertEquals(t3.size(), 2);
    List<AnnouncementPO> t31 = announcementRepo
        .findByTitleOrContentOrTags(new AnnouncementQuery(affairIds, "T3", null,
                Lists.newArrayList(ROLE_ID1)),
            PageRequest.of(0, 10))
        .getContent();
    assertEquals(t31.size(), 2);

    List<AnnouncementPO> query = announcementRepo
        .findByAll("_all", "announcement2 quick", PageRequest.of(0, 10))
        .getContent();
    assertEquals(query.size(), 4);
    List<AnnouncementPO> announcement2_role1 = announcementRepo
        .findByTitleOrContentOrTags(new AnnouncementQuery(affairIds,
                "announcement2 quick", null, Lists.newArrayList(ROLE_ID1)),
            PageRequest.of(0, 10)).getContent();
    assertEquals(announcement2_role1.size(), 2);
  }

  @Test
  public void testChinese() {
    List<AnnouncementPO> back =
        announcementRepo.findByAll("_all", "后端", PageRequest.of(0, 10))
        .getContent();
    assertEquals(back.size(), 4);

    AnnouncementQuery query = new AnnouncementQuery(affairIds, "后端", null,
        Lists.newArrayList(ROLE_ID1));
    List<AnnouncementPO> back2 = announcementRepo
        .findByTitleOrContentOrTags(query, PageRequest.of(0, 10))
        .getContent();
    // use tokenizer, 后端 is one word
    assertEquals(back2.size(), 2);

    List<AnnouncementPO> dev = announcementRepo.findByAll("_all", "开发", PageRequest.of(0, 10))
        .getContent();
    assertEquals(dev.size(), 2);

    AnnouncementQuery query2 = new AnnouncementQuery(affairIds, "开发", null,
        Lists.newArrayList(ROLE_ID1));
    query2.setRoleIds(Lists.newArrayList(ROLE_ID1));
    List<AnnouncementPO> dev2 = announcementRepo
        .findByTitleOrContentOrTags(query2,
            PageRequest.of(0, 10))
        .getContent();
    assertEquals(dev2.size(), 2);
  }

  @Test
  public void testMultiWord() {
    List<AnnouncementPO> brown_dog = announcementRepo
        .findByTitleOrContentOrTags(new AnnouncementQuery(affairIds,
            "BROWN DOG", null, Lists.newArrayList(ROLE_ID1)), PageRequest.of(0, 10))
        .getContent();
    assertEquals(brown_dog.size(), 4);
  }

  @After
  public void tearDown() throws Exception {
//    String indexName = Suffix
//        .timeBasedPattern(AnnouncementPO.class, modifyTime.getTime(), modifyTime.getTime());
//    assertEquals(indexName, "announcement-2016.10*");
//    boolean b = esTemplate.deleteIndex(indexName);
//    assertTrue(b);
  }
}