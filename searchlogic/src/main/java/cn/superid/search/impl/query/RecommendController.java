package cn.superid.search.impl.query;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.user.GuessQuery;
import cn.superid.search.entities.user.user.InterestQuery;
import cn.superid.search.entities.user.user.InterestVO;
import cn.superid.search.entities.user.user.StudentQuery;
import cn.superid.search.entities.user.user.StudentVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.user.affair.AffairRecommendCustom;
import cn.superid.search.impl.entities.user.user.PersonalRecommendCustom;
import cn.superid.search.impl.entities.user.user.UserPO;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ScrolledPage;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for query entities
 *
 * @author zzt
 */
@RestController
@RequestMapping("/inner/recommend")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class RecommendController {

  private static final Logger logger = LoggerFactory.getLogger(RecommendController.class);
  private final PersonalRecommendCustom personalRecommendCustom;
  private final AffairRecommendCustom affairRepo;

  private static void checkPage(PageRequest pageRequest) {
    int pageNum = pageRequest.getPageNumber();
    int pageSize = pageRequest.getPageSize();
    if ((pageNum + 1) * pageSize > 1000) {
      throw new IllegalArgumentException("Invalid page request");
    }
  }

  private static void checkUserId(Long id) {
    if (id == null || id == 0) {
      throw new IllegalArgumentException("Invalid query, no userId");
    }
  }

  @PostMapping({"/like"})
  public PageVO<AffairVO> recommend(@RequestBody GuessQuery query) {
    checkPage(query.getPageRequest());
    checkUserId(query.getUserId());
    Page<UserPO> recommend = personalRecommendCustom.random(query);
    return new PageVO<>(recommend, VoAndPoConversion::toAffairVO, query.getPageRequest(),
        ((ScrolledPage<UserPO>) recommend).getScrollId());
  }

  @PostMapping({"/user/student"})
  public PageVO<StudentVO> queryStudent(@RequestBody StudentQuery query) {
    checkPage(query.getPageRequest());
    checkUserId(query.getUserId());
    Page<UserPO> userPOS = personalRecommendCustom.similarStudent(query);
    return new PageVO<>(userPOS, VoAndPoConversion::toStudentVO, query.getPageRequest());
  }

  @PostMapping({"/user/interest"})
  public PageVO<InterestVO> queryByInterest(@RequestBody InterestQuery query) {
    checkPage(query.getPageRequest());
    checkUserId(query.getUserId());
    Page<UserPO> userPOS = personalRecommendCustom.similarTag(query);
    return new PageVO<>(userPOS, VoAndPoConversion::toInterestVO, query.getPageRequest());
  }

}
