package cn.superid.search.impl.entities.user.role;

import static cn.superid.search.impl.entities.user.role.RoleRepoTest.ALLIANCE1;
import static cn.superid.search.impl.entities.user.role.RoleRepoTest.ALLIANCE2;

import cn.superid.search.impl.save.rolling.Suffix;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepoImplTest {

  @Autowired
  private RoleRepo roleRepo;
  @Autowired
  private ElasticsearchTemplate esTemplate;
  @Autowired
  private Suffix suffix;

  @Before
  public void setUp() throws Exception {
    RoleRepoTest.setUp(roleRepo, suffix, esTemplate);
  }

  @After
  public void tearDown() throws Exception {
    RoleRepoTest.tear(esTemplate);
  }

  @Test
  public void findRoleExcept() throws Exception {
    Page<RolePO> f = roleRepo.findRoleExcept(ALLIANCE1, "zzt架构", PageRequest.of(0, 10));
    Assert.assertEquals(f.getTotalElements(), 2);
    Page<RolePO> s = roleRepo.findRoleExcept(ALLIANCE2, "前端zzt", PageRequest.of(0, 10));
    Assert.assertEquals(s.getTotalElements(), 2);
  }

  @Test
  public void findRoleInterAlliance() throws Exception {
    Page<RolePO> f = roleRepo.findRoleInterAlliance("前端zzt", PageRequest.of(0, 10));
    Assert.assertEquals(f.getTotalElements(), 3);
    Page<RolePO> s = roleRepo.findRoleInterAlliance("后端zzt", PageRequest.of(0, 10));
    Assert.assertEquals(s.getTotalElements(), 3);
  }

}