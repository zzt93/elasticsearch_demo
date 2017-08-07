package cn.superid.search.impl.query.user.affair;

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
public class AffairRepoTest {

  @Autowired
  private AffairRepo affairRepo;

  @Before
  public void setUp() throws Exception {
    AffairPO first = new AffairPO("1", "思目创意").makePath("");
    affairRepo.save(first);
    AffairPO second = new AffairPO("2", "开发部").makePath(first.getPath());
    AffairPO third = new AffairPO("5", "设计部").makePath(first.getPath());
    affairRepo.save(second);
    affairRepo.save(third);
    affairRepo.save(new AffairPO("3", "后端").makePath(second.getPath()));
    affairRepo.save(new AffairPO("4", "前端").makePath(second.getPath()));
    affairRepo.save(new AffairPO("6", "app").makePath(third.getPath()));
    affairRepo.save(new AffairPO("7", "网页").makePath(third.getPath()));
  }

  @Test
  public void findById() throws Exception {
    System.out.println(affairRepo.findById("1"));
    System.out.println(affairRepo.findById("2"));
    System.out.println(affairRepo.findById("3"));
  }

  @Test
  public void findByNameOrPath() throws Exception {
    System.out.println(affairRepo.findByNameOrPath("开发", new PageRequest(0, 10)).getContent());
    System.out.println(affairRepo.findByNameOrPath("开发部", new PageRequest(0, 10)).getContent());
    System.out.println(affairRepo.findByNameOrPath("后端开发", new PageRequest(0, 10)).getContent());
  }

}