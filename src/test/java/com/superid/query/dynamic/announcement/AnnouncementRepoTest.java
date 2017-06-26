package com.superid.query.dynamic.announcement;

import com.google.common.collect.Lists;
import com.superid.query.Tag;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * Created by zzt on 17/6/23.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AnnouncementRepoTest {

    @Autowired
    private AnnouncementRepo announcementRepo;
    private static Tag t1 = new Tag("t1");
    private static Tag t2 = new Tag("t2");

    @Before
    public void save() {
        String role1 = "role1";
        String role2 = "role2";
        Announcement a1 = new Announcement("1", "test1", Lists.newArrayList(t1, t2), role1, role2);

        String role3 = "role3";
        Tag t3 = new Tag("t3");
        Announcement a2 = new Announcement("2", "test2", Lists.newArrayList(t1, t3), role1, role3);

        Announcement a3 = new Announcement("3", "test3", Lists.newArrayList(t2, t3), role2, role3);

        announcementRepo.save(a1);
        announcementRepo.save(a2);
        announcementRepo.save(a3);
    }

    @Test
    public void findAll() {
        announcementRepo.findAll().forEach(System.out::println);
    }

    @Test
    public void findAllByTitleOrPublisherOrModifierOrTagsIn() throws Exception {
        Tag t10 = new Tag("10");
        Tag t20 = new Tag("20");
//        Slice<Announcement> test1 = announcementRepo.findByTitle("test", new PageRequest(0, 10));
//        System.out.println(test1.getContent());
//        List<Announcement> test2 = announcementRepo.findAllByTitle("test1", new PageRequest(0, 10));
//        System.out.println(test2);
//        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("test", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("testx", "", "", Lists.newArrayList(t1, t2), new PageRequest(0, 10)).getContent());
//        System.out.println(announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn("test role1", "", "", Lists.newArrayList(t10, t20), new PageRequest(0, 10)).getContent());

        System.out.println(announcementRepo.findByAll("_all", "test2 role1", new PageRequest(0, 10)).getContent());
    }

    @Test
    public void testChinese() {

    }

}