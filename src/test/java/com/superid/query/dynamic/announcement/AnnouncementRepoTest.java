package com.superid.query.dynamic.announcement;

import com.google.common.collect.Lists;
import com.superid.query.Tag;
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

    private static Tag t1 = new Tag("t1");
    private static Tag t2 = new Tag("t2");
    private static Tag t3 = new Tag("t3");
    @Autowired
    private AnnouncementRepo announcementRepo;

    @Before
    public void save() {
        String role1 = "role1";
        String role2 = "role2";
        Announcement a1 = new Announcement("1", "announcement1", Lists.newArrayList(t1, t2), role1, role2);

        String role3 = "role3";
        Announcement a2 = new Announcement("2", "announcement2", Lists.newArrayList(t1, t3), role1, role3);

        Announcement a3 = new Announcement("3", "announcement3", Lists.newArrayList(t2, t3), role2, role3);

        announcementRepo.save(a1);
        announcementRepo.save(a2);
        announcementRepo.save(a3);

        String role4 = "后端开发";
        String role5 = "前端开发";
        String role6 = "前端架构";
        String role7 = "后端架构";
        announcementRepo.save(new Announcement("4", "后端开发技术", Lists.newArrayList(), role4, role4));
        announcementRepo.save(new Announcement("5", "前端开发技术", Lists.newArrayList(), role5, role5));
        announcementRepo.save(new Announcement("6", "前端人员", Lists.newArrayList(), role6, role6));
        announcementRepo.save(new Announcement("7", "后端人员", Lists.newArrayList(), role7, role7));
    }

    @Test
    public void findAll() {
        announcementRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void findTagsInList() {
        System.out.println(announcementRepo.findByAll("_all", "t1", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("t1", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findByAll("_all", "t2", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("t2", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findByAll("_all", "t3", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("t3", new PageRequest(0, 10)).getContent());
    }

    @Test
    public void findAllByTitleOrPublisherOrModifierOrTagsIn() throws Exception {
//        Tag t10 = new Tag("10");
//        Tag t20 = new Tag("20");
//        Slice<Announcement> test1 = announcementRepo.findByTitle("announcement", new PageRequest(0, 10));
//        System.out.println(test1.getContent());
//        List<Announcement> test2 = announcementRepo.findAllByTitle("announcement1", new PageRequest(0, 10));
//        System.out.println(test2);
//        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("announcement", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("announcementx", "", "", Lists.newArrayList(t1, t2), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("announcement role1", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());

        System.out.println(announcementRepo.findByAll("_all", "announcement2 role1", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("announcement2 role1", new PageRequest(0, 10)).getContent());
    }

    @Test
    public void testChinese() {
        System.out.println(announcementRepo.findByAll("_all", "后端", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("后端", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findByAll("_all", "开发", new PageRequest(0, 10)).getContent());
        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("开发", new PageRequest(0, 10)).getContent());
    }

}