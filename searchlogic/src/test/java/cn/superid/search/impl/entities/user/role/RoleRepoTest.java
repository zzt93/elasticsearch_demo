package cn.superid.search.impl.entities.user.role;

import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.collect.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zzt on 17/6/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepoTest {

  @Autowired
  private RoleRepo roleRepo;
  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;
  private static final Long ownerRoleId = 110l;

  @Before
  public void setUp() throws Exception {
    long taskId = 1L;
    suffix.setSuffix("123");
    MessageReceiverTest.createIfNotExist(esTemplate, RolePO.class);

    roleRepo.save(new RolePO("1", "前端开发", 1L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));
    roleRepo.save(new RolePO("2", "后端开发", 1L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));
    roleRepo.save(new RolePO("6", "前端开发", 2L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));
    roleRepo.save(new RolePO("7", "后端开发", 2L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));

    suffix.setSuffix("234");
    MessageReceiverTest.createIfNotExist(esTemplate, RolePO.class);

    roleRepo.save(new RolePO("3", "前端架构", 2L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));
    roleRepo.save(new RolePO("4", "后端架构", 2L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));
    roleRepo.save(new RolePO("5", "CTO", 3L, 0, Lists.newArrayList(new TagPO("tag")), ownerRoleId));
  }

  @Test
  public void findByTitle() throws Exception {
  }

  @Test
  public void findByAffairIdAndTitle() throws Exception {
    suffix.setSuffix("123");
    System.out
        .println(roleRepo.findByAffairIdAndTitle(2L, "前端", PageRequest.of(0, 10)).getContent());
    suffix.setSuffix("234");
    System.out
        .println(roleRepo.findByAffairIdAndTitle(2L, "前端", PageRequest.of(0, 10)).getContent());
  }

  @Test
  public void findByTitleAndAffairIdNot() throws Exception {
    System.out
        .println(roleRepo.findByTitleAndAffairIdNot("前端", 1L, PageRequest.of(0, 5)).getContent());
  }

}