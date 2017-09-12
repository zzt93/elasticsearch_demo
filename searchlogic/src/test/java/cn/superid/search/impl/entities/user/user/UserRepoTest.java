package cn.superid.search.impl.entities.user.user;

import static org.junit.Assert.assertTrue;

import cn.superid.search.impl.entities.TagPO;
import java.util.List;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zzt
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepoTest {

  private static final TagPO TAG_1 = new TagPO("tag1");
  private static final TagPO TAG_2 = new TagPO("tag2");
  private static final TagPO TAG_3 = new TagPO("tag3");

  @Autowired
  private UserRepo userRepo;

  @Before
  public void setUp() throws Exception {
    // given
    userRepo.save(new UserPO("1", "user1", "1", Lists.newArrayList(TAG_1, TAG_2)));
    userRepo.save(new UserPO("3", "user1", "3", Lists.newArrayList(TAG_3, TAG_2)));
    userRepo.save(new UserPO("2", "user2", "2", Lists.newArrayList(TAG_1, TAG_3)));
  }


  @Test
  public void findTop20ByTagsIn() throws Exception {
    // when
    List<UserPO> top20ByTagsIn = userRepo.findByTagsIn(TAG_1.getDes(), UserService.TOP20).getContent();
    // then
    assertTrue(top20ByTagsIn.size() == 2);

    // when
    List<UserPO> top20ByTagsIn2 = userRepo.findByTagsIn(TAG_2.getDes(), UserService.TOP20).getContent();
    // then
    assertTrue(top20ByTagsIn2.size() == 2);

    // when
    List<UserPO> top20ByTagsIn3 = userRepo.findByTagsIn(TAG_3.getDes(), UserService.TOP20).getContent();
    // then
    assertTrue(top20ByTagsIn3.size() == 2);


  }

  @Test
  public void findTop20ByUsernameOrSuperId() throws Exception {
    // when
    List<UserPO> user1 = userRepo.findByUsernameOrSuperId("user1", "user1", UserService.TOP20).getContent();
    // then
    assertTrue(user1.size() == 2);
  }

}