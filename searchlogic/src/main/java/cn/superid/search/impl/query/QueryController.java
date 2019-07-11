package cn.superid.search.impl.query;

import static cn.superid.search.impl.query.QueryHelper.wildcard;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.StringQuery;
import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.time.audit.AuditQuery;
import cn.superid.search.entities.time.audit.AuditVO;
import cn.superid.search.entities.time.chat.ChatIdsQuery;
import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.entities.time.chat.MessagesVO;
import cn.superid.search.entities.user.affair.AffairQuery;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.affair.AllianceVO;
import cn.superid.search.entities.user.affair.InAllianceVO;
import cn.superid.search.entities.user.affair.MenkorVO;
import cn.superid.search.entities.user.affair.OutAllianceVO;
import cn.superid.search.entities.user.file.FileQuery;
import cn.superid.search.entities.user.file.FileSearchVO;
import cn.superid.search.entities.user.process.ProcessCountVO;
import cn.superid.search.entities.user.process.ProcessQuery;
import cn.superid.search.entities.user.process.ProcessVO;
import cn.superid.search.entities.user.role.RoleQuery;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.target.TargetQuery;
import cn.superid.search.entities.user.target.TargetVO;
import cn.superid.search.entities.user.task.TaskQuery;
import cn.superid.search.entities.user.task.TaskVO;
import cn.superid.search.entities.user.user.UserVO;
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
import cn.superid.search.impl.entities.user.file.FilePO;
import cn.superid.search.impl.entities.user.file.FileRepo;
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
import cn.superid.search.impl.entities.user.warehouse.MaterialPO;
import cn.superid.search.impl.entities.user.warehouse.MaterialRepo;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for query entities
 *
 * @author zzt
 */
@RestController
@RequestMapping("/query")
public class QueryController {

  private static final int PAGE_SIZE = 10;
  private static final Logger logger = LoggerFactory.getLogger(QueryController.class);
  private static final PageRequest TOP20 = PageRequest.of(0, 20);
  private final UserService userService;
  private final MessagesRepo messagesRepo;
  private final FileRepo fileRepo;
  private final RoleRepo roleRepo;
  private final AnnouncementRepo announcementRepo;
  private final ProcessRepo processRepo;
  private final TaskRepo taskRepo;
  private final AffairRepo affairRepo;
  private final TargetRepo targetRepo;
  private final MaterialRepo materialRepo;
  private final AuditRepo auditRepo;
  private final Suffix suffix;
  private final ElasticsearchConverter elasticsearchConverter;

  @Value("${mobile}")
  private String mobileRegex;
  private Pattern mobile;

  @Autowired
  public QueryController(UserService userService, MessagesRepo messagesRepo, FileRepo fileRepo,
      RoleRepo roleRepo,
      AnnouncementRepo announcementRepo, ProcessRepo processRepo, TaskRepo taskRepo,
      AffairRepo affairRepo,
      TargetRepo targetRepo, MaterialRepo materialRepo,
      AuditRepo auditRepo,
      Suffix suffix, ElasticsearchConverter elasticsearchConverter) {
    this.userService = userService;
    this.messagesRepo = messagesRepo;
    this.fileRepo = fileRepo;
    this.roleRepo = roleRepo;
    this.announcementRepo = announcementRepo;
    this.processRepo = processRepo;
    this.taskRepo = taskRepo;
    this.affairRepo = affairRepo;
    this.targetRepo = targetRepo;
    this.materialRepo = materialRepo;
    this.auditRepo = auditRepo;
    this.suffix = suffix;
    this.elasticsearchConverter = elasticsearchConverter;
  }

  @PostConstruct
  public void initPattern() {
    mobile = Pattern.compile(mobileRegex);
  }

  private static void checkPage(PageRequest pageRequest) {
    int pageNum = pageRequest.getPageNumber();
    int pageSize = pageRequest.getPageSize();
    if ((pageNum + 1) * pageSize > 1000) {
      throw new IllegalArgumentException("Invalid page request");
    }
  }

  private static void checkAllianceId(Long id) {
    if (id == null || id == 0) {
      throw new IllegalArgumentException("Invalid query, no allianceId");
    }
  }

  @PostMapping("/file")
  public List<FileSearchVO> queryFile(@RequestBody FileQuery query) {
    Long affairId = query.getAffairId();
    if (affairId == null || affairId == 0) {
      throw new IllegalArgumentException("Invalid page request");
    }
    Page<FilePO> files = fileRepo.findByNameOrUploadRoleName(query);
    if (files == null) {
      return Collections.emptyList();
    }
    return files.stream().map(VoAndPoConversion::toVO).collect(Collectors.toList());
  }

  @PostMapping("/announcement")
  public PageVO<AnnouncementVO> queryAnnouncement(@RequestBody AnnouncementQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    checkPage(pageRequest);
    Page<AnnouncementPO> res = announcementRepo
        .findByTitleOrContentOrTags(query, pageRequest);
    return new PageVO<>(res, VoAndPoConversion::toVO);
  }

  @PostMapping("/material")
  public PageVO<MaterialVO> queryMaterial(@RequestBody MaterialQuery query) {
    Page<MaterialPO> materialPOS;
    ScrollMapper mapper = new ScrollMapper(elasticsearchConverter);
    if (query.getScrollQuery() == null) {
      checkPage(query.getPageRequest());
      checkAllianceId(query.getAllianceId());

      materialPOS = materialRepo
          .findByAllInfo(query, query.getPageRequest(), mapper);
      return new PageVO<>(materialPOS, VoAndPoConversion::toVO, mapper.getScrollId());
    } else {
      materialPOS = materialRepo.findByAllInfo(query.getScrollQuery(), mapper);
      return new PageVO<>(materialPOS, VoAndPoConversion::toVO, mapper.getScrollId());
    }
  }

  @PostMapping("/material/tags")
  public PageVO<MaterialVO> queryMaterialTags(@RequestBody MaterialQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    suffix.setSuffix(String.valueOf(query.getAllianceId() / MaterialPO.CLUSTER_SIZE));
    Page<MaterialPO> byTagsIn = materialRepo
        .findByAllianceIdAndTagsIn(query.getAllianceId(), query.getTags(), query.getPageRequest());
    return new PageVO<>(byTagsIn, VoAndPoConversion::toVO);
  }

  @PostMapping("/affair")
  public MenkorVO queryAffair(@RequestBody AffairQuery query) {
    checkPage(query.getPageRequest());

    UserVO byMobile = null;
    if (query.getPageRequest().getPageNumber() == 0 && mobile.matcher(query.getQuery()).find()) {
      byMobile = userService.findByMobile(query.getQuery());
    }
    Page<AffairPO> page = affairRepo.findAny(query.getQuery(), query.getPageRequest());
    return new MenkorVO(new PageVO<>(page, VoAndPoConversion::toVO), byMobile);
  }

  @PostMapping("/alliance")
  public InAllianceVO queryInAlliance(@RequestBody AffairQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    Page<UserPO> users = userService.findByUserName(query.getQuery(), query.getPageRequest());
    Page<AffairPO> page = affairRepo.findByNameAndAllianceId(query.getQuery(), query.getAllianceId(), query.getPageRequest());
    return new InAllianceVO(new PageVO<>(page, VoAndPoConversion::toVO), new PageVO<>(users, VoAndPoConversion::toVO));
  }

  @PostMapping("/menkor")
  public OutAllianceVO queryOutAlliance(@RequestBody AffairQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    List<UserPO> users = userService.findByUserName(query.getQuery(), query.getPageRequest()).getContent();
    List<AllianceVO> affairs = affairRepo.findAlliance(query.getQuery(), query.getAllianceId(), query.getPageRequest())
        .stream().map(VoAndPoConversion::toAlliance).collect(Collectors.toList());
    return new OutAllianceVO(affairs, users.stream().map(VoAndPoConversion::toVO).collect(Collectors.toList()));
  }

  @PostMapping("/affair/tags")
  public PageVO<AffairVO> queryAffairTags(@RequestBody AffairQuery affairInfo) {
    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo
        .findByTagsIn(affairInfo.getTags(),
            affairInfo.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
  }

  @PostMapping("/affair/superId")
  public PageVO<AffairVO> queryAffairSuperId(@RequestBody AffairQuery affairInfo) {
    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo
        .findBySuperId(affairInfo.getQuery(), affairInfo.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
  }

  @PostMapping("/affair/name")
  public PageVO<AffairVO> queryAffairName(@RequestBody AffairQuery affairInfo) {
    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo
        .findByName(wildcard(affairInfo.getQuery()), affairInfo.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
  }

  @PostMapping("/role")
  public PageVO<RoleVO> queryRole(@RequestBody RoleQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());
    Preconditions.checkState(false);

    Page<RolePO> byAll = roleRepo.findByAll(query.getQuery());
    return new PageVO<>(byAll, VoAndPoConversion::toVO);
  }

  @PostMapping("/role/tags")
  public PageVO<RoleVO> queryRoleTags(@RequestBody RoleQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    suffix.setSuffix(String.valueOf(query.getAllianceId() / RolePO.CLUSTER_SIZE));
    String[] tagPOS = query.getTags();
    Page<RolePO> byTagsIn = roleRepo.findByTagsIn(tagPOS, query.getPageRequest());
    return new PageVO<>(byTagsIn, VoAndPoConversion::toVO);
  }

  @PostMapping("/role/except_alliance")
  public PageVO<RoleVO> queryRoleExceptAlliance(@RequestBody RoleQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());
    Page<RolePO> roleExcept = roleRepo
        .findRoleExcept(query.getAllianceId(), query.getQuery(), query.getPageRequest());
    return new PageVO<>(roleExcept, VoAndPoConversion::toVO);
  }

  @PostMapping("/user")
  public List<UserVO> findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(@RequestBody StringQuery query) {
    return userService.findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(query.getQuery());
  }

  @PostMapping("/chat")
  public PageVO<MessagesVO> queryChat(@RequestBody ChatQuery chatQuery) {
    checkPage(chatQuery.getPageRequest());
    Page<MessagesPO> byMessage = messagesRepo.findByMessage(chatQuery, chatQuery.getPageRequest());
    return new PageVO<>(byMessage, VoAndPoConversion::toVO);
  }

  @PostMapping("/chat/count")
  public Map<String, Long> countMessage(@RequestBody ChatIdsQuery chatQuery) {
    return messagesRepo.countMessage(chatQuery);
  }

  @PostMapping("/task")
  public PageVO<TaskVO> queryTask(@RequestBody TaskQuery taskQuery) {
    checkPage(taskQuery.getPageRequest());
    Page<TaskPO> byMessage = taskRepo.findByAll(taskQuery);
    return new PageVO<>(byMessage, VoAndPoConversion::toVO);
  }

  @PostMapping("/audit")
  public PageVO<AuditVO> queryAudit(@RequestBody AuditQuery auditQuery) {
    checkPage(auditQuery.getPageRequest());
    Page<AuditPO> byQuery = auditRepo.findByQuery(auditQuery);
    return new PageVO<>(byQuery, VoAndPoConversion::toVO);
  }

  @PostMapping("/target")
  public List<TargetVO> queryTarget(@RequestBody TargetQuery targetQuery) {
    List<Long> affairs = targetQuery.getAffairs();
    Preconditions.checkArgument(affairs!=null, "No affair provided");
    List<TargetPO> byQuery = targetRepo.findByNameAndAffairIdIn(targetQuery);
    return byQuery.stream().map(VoAndPoConversion::toVO).collect(Collectors.toList());
  }

  @GetMapping("/user/tags")
  public List<UserVO> queryUserByTag(@RequestParam String query) {
    return userService.findTop20ByTags(query);
  }

  @GetMapping("/user/username")
  public List<UserVO> queryUserByUsername(@RequestParam String query) {
    return userService.findTop20ByUserNameOrSuperId(query);
  }


  @GetMapping("/role/all")
  public Page<RolePO> queryAllRole(@RequestParam String role) {
    return roleRepo.findRoleInterAlliance(role, PageRequest.of(0, PAGE_SIZE));
  }

  @PostMapping("/process")
  public PageVO<ProcessVO> queryProcessInner(@RequestBody ProcessQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    checkPage(pageRequest);
    Page<ProcessPO> res = processRepo
        .find(query, pageRequest);
    return new PageVO<>(res, VoAndPoConversion::toVO);
  }

  @PostMapping("/process/count")
  public ProcessCountVO countProcess(@RequestBody ProcessQuery query) {
    return processRepo.count(query);
  }
}
