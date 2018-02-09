package cn.superid.search.impl.entities.user.role;

import static org.junit.Assert.assertEquals;

import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
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
 * Created by zzt on 17/6/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepoTest {

  public static final long ALLIANCE1 = 123000;
  public static final long ALLIANCE2 = 234000;
  @Autowired
  private RoleRepo roleRepo;
  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;
  private static final Long ownerRoleId = 110L;
  private static final byte type = 0;

  @Before
  public void setUp() throws Exception {
    suffix.setSuffix(""+ ALLIANCE1/RolePO.CLUSTER_SIZE);
    MessageReceiverTest.createIfNotExist(esTemplate, RolePO.class);

    roleRepo.save(new RolePO("1", "前端开发", 1L, type, new String[]{"tag"}, ownerRoleId));
    roleRepo.save(new RolePO("2", "后端开发", 1L, type, new String[]{"tag"}, ownerRoleId));
    roleRepo.save(new RolePO("6", "前端开发", 2L, type, new String[]{"tag"}, ownerRoleId));
    roleRepo.save(new RolePO("7", "后端开发", 2L, type, new String[]{"tag"}, ownerRoleId));

    suffix.setSuffix(""+ ALLIANCE2/RolePO.CLUSTER_SIZE);
    MessageReceiverTest.createIfNotExist(esTemplate, RolePO.class);

    roleRepo.save(new RolePO("3", "前端架构", 2L, type, new String[]{"tag"}, ownerRoleId));
    roleRepo.save(new RolePO("4", "后端架构", 2L, type, new String[]{"tag"}, ownerRoleId));
    roleRepo.save(new RolePO("5", "CTO", 3L, type, new String[]{"tag"}, ownerRoleId));
  }

  @Test
  public void findByTitle() throws Exception {
  }

  @Test
  public void findByAffairIdAndTitle() throws Exception {
    suffix.setSuffix("123");
    List<RolePO> front = roleRepo.findByAffairIdAndTitle(2L, "前端", PageRequest.of(0, 10)).getContent();
    assertEquals(front.size(), 1);
    suffix.setSuffix("234");
    List<RolePO> front2 = roleRepo.findByAffairIdAndTitle(2L, "前端", PageRequest.of(0, 10)).getContent();
    assertEquals(front2.size(), 1);
  }

  @Test
  public void findByTitleAndAffairIdNot() throws Exception {
    List<RolePO> front = roleRepo.findByTitleAndAffairIdNot("前端", 1L, PageRequest.of(0, 5))
        .getContent();
    assertEquals(front.size(), 1);
  }


}