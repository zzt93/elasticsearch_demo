package cn.superid.search.impl.query;

import static cn.superid.search.impl.query.QueryHelper.wildcard;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.StringQuery;
import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.entities.time.chat.MessagesVO;
import cn.superid.search.entities.user.affair.AffairQuery;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.file.FileQuery;
import cn.superid.search.entities.user.file.FileSearchVO;
import cn.superid.search.entities.user.role.RoleQuery;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.task.TaskQuery;
import cn.superid.search.entities.user.task.TaskVO;
import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.entities.user.warehouse.MaterialQuery;
import cn.superid.search.entities.user.warehouse.MaterialVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.entities.time.chat.MessagesPO;
import cn.superid.search.impl.entities.time.chat.MessagesRepo;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.affair.AffairRepo;
import cn.superid.search.impl.entities.user.file.FilePO;
import cn.superid.search.impl.entities.user.file.FileRepo;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import cn.superid.search.impl.entities.user.task.TaskPO;
import cn.superid.search.impl.entities.user.task.TaskRepo;
import cn.superid.search.impl.entities.user.user.UserService;
import cn.superid.search.impl.entities.user.warehouse.MaterialPO;
import cn.superid.search.impl.entities.user.warehouse.MaterialRepo;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
  private final UserService userService;
  private final MessagesRepo messagesRepo;
  private final FileRepo fileRepo;
  private final RoleRepo roleRepo;
  private final AnnouncementRepo announcementRepo;
  private final TaskRepo taskRepo;
  private final AffairRepo affairRepo;
  private final MaterialRepo materialRepo;
  private final Suffix suffix;
  private final ElasticsearchConverter elasticsearchConverter;

  @Autowired
  public QueryController(UserService userService, MessagesRepo messagesRepo, FileRepo fileRepo,
      RoleRepo roleRepo,
      AnnouncementRepo announcementRepo, TaskRepo taskRepo, AffairRepo affairRepo,
      MaterialRepo materialRepo, Suffix suffix, ElasticsearchConverter elasticsearchConverter) {
    this.userService = userService;
    this.messagesRepo = messagesRepo;
    this.fileRepo = fileRepo;
    this.roleRepo = roleRepo;
    this.announcementRepo = announcementRepo;
    this.taskRepo = taskRepo;
    this.affairRepo = affairRepo;
    this.materialRepo = materialRepo;
    this.suffix = suffix;
    this.elasticsearchConverter = elasticsearchConverter;
  }

  private static void checkPage(PageRequest pageRequest) {
    int pageNum = pageRequest.getPageNumber();
    int pageSize = pageRequest.getPageSize();
    if ((pageNum + 1) * pageSize > 1000) {
      throw new IllegalArgumentException("Invalid page request");
    }
  }

  private static void checkAllianceId(Long id) {
    if (id == null) {
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
  public PageVO<AffairVO> queryAffair(@RequestBody AffairQuery query) {
    checkPage(query.getPageRequest());

    Page<AffairPO> page = affairRepo.findAny(query.getQuery(), query.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
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

  @PostMapping("/task")
  public PageVO<TaskVO> queryTask(@RequestBody TaskQuery taskQuery) {
    checkPage(taskQuery.getPageRequest());
    Page<TaskPO> byMessage = taskRepo.findByAll(taskQuery);
    return new PageVO<>(byMessage, VoAndPoConversion::toVO);
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
}
