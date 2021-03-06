package cn.superid.search.impl.entities.user.file;

import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FileRepoImplTest {

  @Autowired
  private FileRepo fileRepo;
  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;

  @Before
  public void setUp() throws Exception {
    suffix.setSuffix("1111");
    MessageReceiverTest.createIfNotExist(esTemplate, FilePO.class);

    fileRepo.save(new FilePO("1", "fileName", "1234", 0));
  }

  @Test
  public void updateFileName() throws Exception {
  }

  @Test
  public void findByNameOrUploadRoleName() throws Exception {
  }

}