package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author zzt
 */
@Service
public class UserService {

  static final PageRequest TOP20 = PageRequest.of(0, 20);
  private static final Logger logger = LoggerFactory.getLogger(UserService.class);
  private final UserRepo userRepo;

  @Autowired
  public UserService(UserRepo userRepo) {
    this.userRepo = userRepo;
  }

  public List<UserVO> findTop20ByUserNameOrSuperId(String query) {
    return userRepo.findByUsernameOrSuperId(query, query, TOP20).getContent()
        .stream()
        .map(VoAndPoConversion::toVO)
        .collect(Collectors.toList());
  }

  public List<UserVO> findTop20ByTags(String query) {
    return userRepo.findByTagsIn(query, TOP20).getContent()
        .stream()
        .map(VoAndPoConversion::toVO)
        .collect(Collectors.toList());
  }

  public List<UserVO> findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(String query) {
    return userRepo.findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(query, TOP20).getContent()
        .stream()
        .map(VoAndPoConversion::toVO)
        .collect(Collectors.toList());
  }

  public UserVO findByMobile(String query) {
    List<UserPO> byMobile = userRepo.findByMobile(query);
    if (byMobile == null || byMobile.isEmpty()) {
      return null;
    }
    if (byMobile.size() > 1) {
      logger.error("Dup mobile for {}", byMobile);
    }
    UserPO user = byMobile.get(0);
    return new UserVO(user.getId()).setMobile(user.getMobile());
  }
}
