package cn.superid.search.impl.query.user.role;

import cn.superid.search.entities.user.Role;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by zzt on 17/6/27.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RoleRepoTest {

  @Autowired
  private RoleRepo roleRepo;

  @Before
  public void setUp() throws Exception {
    long taskId = 1L;
    roleRepo.save(new Role("1", "前端开发", false, 1L, taskId));
    roleRepo.save(new Role("2", "后端开发", false, 1L, taskId));
    roleRepo.save(new Role("3", "前端架构", false, 2L, taskId));
    roleRepo.save(new Role("4", "后端架构", false, 2L, taskId));
    roleRepo.save(new Role("5", "CTO", false, 3L, taskId));
  }

  @Test
  public void findByTitle() throws Exception {
  }

  @Test
  public void findByAffairIdAndTitle() throws Exception {
  }

  @Test
  public void findByTitleAndAffairIdNot() throws Exception {
    System.out
        .println(roleRepo.findByTitleAndAffairIdNot("前端", 1L, new PageRequest(0, 5)).getContent());
  }

}