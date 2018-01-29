package cn.superid.search.impl.entities.user.user;

import static org.junit.Assert.assertTrue;

import java.util.List;
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

  private static final String TAG_1 = "tag1";
  private static final String TAG_2 = "tag2";
  private static final String TAG_3 = "tag3";

  @Autowired
  private UserRepo userRepo;

  @Before
  public void setUp() throws Exception {
    // given
    userRepo.save(new UserPO("1", "user1", "1", new String[]{TAG_1, TAG_2}));
    userRepo.save(new UserPO("3", "user1", "3", new String[]{TAG_1, TAG_3}));
    userRepo.save(new UserPO("2", "user2", "2", new String[]{TAG_2, TAG_3}));
  }


  @Test
  public void findTop20ByTagsIn() throws Exception {
    // when
    List<UserPO> top20ByTagsIn = userRepo.findByTagsIn(TAG_1, UserService.TOP20).getContent();
    // then
    assertTrue(top20ByTagsIn.size() == 2);

    // when
    List<UserPO> top20ByTagsIn2 = userRepo.findByTagsIn(TAG_2, UserService.TOP20).getContent();
    // then
    assertTrue(top20ByTagsIn2.size() == 2);

    // when
    List<UserPO> top20ByTagsIn3 = userRepo.findByTagsIn(TAG_3, UserService.TOP20).getContent();
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