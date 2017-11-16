package cn.superid.search.impl.entities.user.warehouse;

import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.save.MessageReceiverTest;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.collect.Lists;
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
public class MaterialRepoTest {

  private static final byte type = 0;
  @Autowired
  private MaterialRepo materialRepo;
  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate esTemplate;

  @Before
  public void setUp() throws Exception {
    suffix.setSuffix("1111");
    MessageReceiverTest.createIfNotExist(esTemplate, MaterialPO.class);

    materialRepo.save(
        new MaterialPO("1", "computer", Lists.newArrayList(new TagPO("mac")), 1L, 1111L, type,
            type));
  }

  @Test
  public void findByNameOrTagsIn() throws Exception {
  }

}