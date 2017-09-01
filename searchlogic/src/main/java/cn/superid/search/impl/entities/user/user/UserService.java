package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * @author zzt
 */
@Service
public class UserService {

  static final PageRequest TOP20 = PageRequest.of(0, 20);

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

  public List<UserVO> findTop20ByUsernameOrSuperIdOrTags(String query) {
    return userRepo.findByUserNameOrSuperIdOrTagsIn(query, TOP20).getContent()
        .stream()
        .map(VoAndPoConversion::toVO)
        .collect(Collectors.toList());
  }
}
