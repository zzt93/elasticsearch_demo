package cn.superid.search.impl.query;

import cn.superid.search.entities.MyQuery;
import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.time.announcement.MyAnnQuery;
import cn.superid.search.entities.time.audit.AuditQuery;
import cn.superid.search.entities.time.audit.AuditVO;
import cn.superid.search.entities.time.chat.ChatIdsQuery;
import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.entities.time.chat.MessagesVO;
import cn.superid.search.entities.user.affair.AffairQuery;
import cn.superid.search.entities.user.affair.InAllianceVO;
import cn.superid.search.entities.user.process.ProcessQuery;
import cn.superid.search.entities.user.process.ProcessVO;
import cn.superid.search.entities.user.role.RoleQuery;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.target.TargetQuery;
import cn.superid.search.entities.user.target.TargetVO;
import cn.superid.search.entities.user.task.TaskQuery;
import cn.superid.search.entities.user.task.TaskVO;
import cn.superid.search.entities.user.warehouse.MaterialQuery;
import cn.superid.search.entities.user.warehouse.MaterialVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.entities.time.audit.AuditPO;
import cn.superid.search.impl.entities.time.audit.AuditRepo;
import cn.superid.search.impl.entities.time.chat.MessagesPO;
import cn.superid.search.impl.entities.time.chat.MessagesRepo;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.affair.AffairRepo;
import cn.superid.search.impl.entities.user.process.ProcessPO;
import cn.superid.search.impl.entities.user.process.ProcessRepo;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import cn.superid.search.impl.entities.user.target.TargetPO;
import cn.superid.search.impl.entities.user.target.TargetRepo;
import cn.superid.search.impl.entities.user.task.TaskPO;
import cn.superid.search.impl.entities.user.task.TaskRepo;
import cn.superid.search.impl.entities.user.user.UserPO;
import cn.superid.search.impl.entities.user.user.UserService;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
@RequestMapping("/inner/my")
@RequiredArgsConstructor(onConstructor_={@Autowired})

public class MyQueryController {

  private static final Logger logger = LoggerFactory.getLogger(MyQueryController.class);
  private final UserService userService;
  private final MessagesRepo messagesRepo;
  private final RoleRepo roleRepo;
  private final AnnouncementRepo announcementRepo;
  private final ProcessRepo processRepo;
  private final TaskRepo taskRepo;
  private final AffairRepo affairRepo;
  private final TargetRepo targetRepo;
  private final AuditRepo auditRepo;


  private static void checkPage(PageRequest pageRequest) {
    Preconditions.checkArgument(pageRequest != null, "no pageRequest");
    int pageNum = pageRequest.getPageNumber();
    int pageSize = pageRequest.getPageSize();
    if ((pageNum + 1) * pageSize > 1000) {
      throw new IllegalArgumentException("Invalid page request");
    }
  }

  private static void checkMy(MyQuery info) {
    Preconditions.checkArgument(info.getRoles() != null && info.getRoles().size() > 0, "no roles");
  }

  private static void checkAllianceId(Long id) {
    if (id == null || id == 0) {
      throw new IllegalArgumentException("Invalid query, no allianceId");
    }
  }

  @PostMapping("/announcement")
  public PageVO<AnnouncementVO> queryAnnouncement(@RequestBody MyAnnQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    checkPage(pageRequest);
    checkMy(query);

    Page<AnnouncementPO> res = announcementRepo.myAnn(query, pageRequest);
    return new PageVO<>(res, VoAndPoConversion::toVO, query.getPageRequest());
  }

  @PostMapping("/material")
  public PageVO<MaterialVO> queryMaterial(@RequestBody MaterialQuery query) {
    return null;
  }

  @PostMapping("/alliance")
  public InAllianceVO queryInAlliance(@RequestBody AffairQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    Page<UserPO> users = userService.findByUserName(query.getQuery(), query.getPageRequest());
    Page<AffairPO> page = affairRepo.findByNameAndAllianceId(query.getQuery(), query.getAllianceId(), query.getPageRequest());
    return new InAllianceVO(new PageVO<>(page, VoAndPoConversion::toVO, query.getPageRequest()), new PageVO<>(users, VoAndPoConversion::toVO,
        query.getPageRequest()));
  }

  @PostMapping("/role")
  public PageVO<RoleVO> queryRole(@RequestBody RoleQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    Page<RolePO> byAll = roleRepo.findByAll(query);
    return new PageVO<>(byAll, VoAndPoConversion::toVO, query.getPageRequest());
  }

  @PostMapping("/chat")
  public PageVO<MessagesVO> queryChat(@RequestBody ChatQuery chatQuery) {
    checkPage(chatQuery.getPageRequest());
    Page<MessagesPO> byMessage = messagesRepo.findByMessage(chatQuery, chatQuery.getPageRequest());
    return new PageVO<>(byMessage, VoAndPoConversion::toVO, chatQuery.getPageRequest());
  }

  @PostMapping("/chat/count")
  public Map<String, Long> countMessage(@RequestBody ChatIdsQuery chatQuery) {
    return messagesRepo.countMessage(chatQuery);
  }

  @PostMapping("/task")
  public PageVO<TaskVO> queryTask(@RequestBody TaskQuery taskQuery) {
    checkPage(taskQuery.getPageRequest());
    Page<TaskPO> byMessage = taskRepo.findByAll(taskQuery);
    return new PageVO<>(byMessage, VoAndPoConversion::toVO, taskQuery.getPageRequest());
  }

  @PostMapping("/audit")
  public PageVO<AuditVO> queryAudit(@RequestBody AuditQuery auditQuery) {
    checkPage(auditQuery.getPageRequest());
    Page<AuditPO> byQuery = auditRepo.findByQuery(auditQuery);
    return new PageVO<>(byQuery, VoAndPoConversion::toVO, auditQuery.getPageRequest());
  }


  @PostMapping("/target")
  public List<TargetVO> queryTarget(@RequestBody TargetQuery targetQuery) {
    List<Long> affairs = targetQuery.getAffairs();
    Preconditions.checkArgument(affairs!=null, "No affair provided");
    List<TargetPO> byQuery = targetRepo.findByNameAndAffairIdIn(targetQuery);
    return byQuery.stream().map(VoAndPoConversion::toVO).collect(Collectors.toList());
  }

  @PostMapping("/process")
  public PageVO<ProcessVO> queryProcessInner(@RequestBody ProcessQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    checkPage(pageRequest);
    Page<ProcessPO> res = processRepo
        .find(query, pageRequest);
    return new PageVO<>(res, VoAndPoConversion::toVO, query.getPageRequest());
  }


}
