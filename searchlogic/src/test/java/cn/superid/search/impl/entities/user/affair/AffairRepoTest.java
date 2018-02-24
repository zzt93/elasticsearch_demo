package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
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
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AffairRepoTest {

  public static final String SUFFIX = "-1111";
  @Autowired
  private AffairRepo affairRepo;
  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;


  @Before
  public void setUp() throws Exception {
    suffix.setSuffix(SUFFIX);
    MessageReceiverTest.createIfNotExist(esTemplate, AffairPO.class);

    AffairPO first = new AffairPO("1", "思目创意");
    affairRepo.save(first);
    AffairPO second = new AffairPO("2", "开发部");
    AffairPO third = new AffairPO("5", "设计部");
    affairRepo.save(second);
    affairRepo.save(third);
    affairRepo.save(new AffairPO("3", "后端"));
    affairRepo.save(new AffairPO("4", "前端"));
    affairRepo.save(new AffairPO("6", "app"));
    affairRepo.save(new AffairPO("7", "网页"));
  }

  @Test
  public void findById() throws Exception {
    System.out.println(affairRepo.findById("1"));
    System.out.println(affairRepo.findById("2"));
    System.out.println(affairRepo.findById("3"));
  }

  @Test
  public void findAny() throws Exception {
    System.out.println(affairRepo.findAny("开发", PageRequest.of(0, 10)).getContent());
    System.out.println(affairRepo.findAny("开发部", PageRequest.of(0, 10)).getContent());
    System.out.println(affairRepo.findAny("后端开发", PageRequest.of(0, 10)).getContent());
  }

  @Test //SUPERID-313
  public void bugFix313() throws Exception {
    suffix.setSuffix("*");
    System.out.println(affairRepo.findAny("1", PageRequest.of(0, 10)).getContent());
  }

  @After
  public void tearDown() throws Exception {
    esTemplate.deleteIndex(Suffix.indexName(AffairPO.class, SUFFIX));
  }
}