package cn.superid.search.impl.entities.user.role;

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
public class RoleRepoImplTest {

  @Autowired
  private RoleRepo roleRepo;

  @Before
  public void setUp() throws Exception {

  }

  @Test
  public void findRoleExcept() throws Exception {
    System.out.println(roleRepo.findRoleExcept(RoleRepoTest.ALLIANCE1, "架构", PageRequest.of(0, 10)).getContent());
    System.out.println(roleRepo.findRoleExcept(RoleRepoTest.ALLIANCE2, "前端", PageRequest.of(0, 10)).getContent());
  }

  @Test
  public void findRoleInterAlliance() throws Exception {
    System.out.println(roleRepo.findRoleInterAlliance("前端", PageRequest.of(0, 10)).getContent());
    System.out.println(roleRepo.findRoleInterAlliance("架构", PageRequest.of(0, 10)).getContent());
  }

}