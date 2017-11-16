package cn.superid.search.impl.query;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.user.affair.AffairQuery;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.file.FileQuery;
import cn.superid.search.entities.user.file.FileSearchVO;
import cn.superid.search.entities.user.role.RoleQuery;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.entities.user.warehouse.MaterialQuery;
import cn.superid.search.entities.user.warehouse.MaterialVO;
import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.entities.time.chat.ChatPO;
import cn.superid.search.impl.entities.time.chat.ChatRepo;
import cn.superid.search.impl.entities.time.task.TaskRepo;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.affair.AffairRepo;
import cn.superid.search.impl.entities.user.file.FilePO;
import cn.superid.search.impl.entities.user.file.FileRepo;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import cn.superid.search.impl.entities.user.user.UserService;
import cn.superid.search.impl.entities.user.warehouse.MaterialPO;
import cn.superid.search.impl.entities.user.warehouse.MaterialRepo;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.collect.Lists;
import java.util.Date;
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
  private final ChatRepo chatRepo;
  private final FileRepo fileRepo;
  private final RoleRepo roleRepo;
  private final AnnouncementRepo announcementRepo;
  private final TaskRepo taskRepo;
  private final AffairRepo affairRepo;
  private final MaterialRepo materialRepo;
  private final Suffix suffix;
  private final ElasticsearchConverter elasticsearchConverter;

  @Autowired
  public QueryController(UserService userService, ChatRepo chatRepo, FileRepo fileRepo,
      RoleRepo roleRepo,
      AnnouncementRepo announcementRepo, TaskRepo taskRepo, AffairRepo affairRepo,
      MaterialRepo materialRepo, Suffix suffix, ElasticsearchConverter elasticsearchConverter) {
    this.userService = userService;
    this.chatRepo = chatRepo;
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
    List<FilePO> files = fileRepo
        .findByNameOrUploadRoleName(query.getLevel(), query.getQuery(), query.getAllianceId(), affairId);
    return files.stream().map(VoAndPoConversion::toVO).collect(Collectors.toList());
  }

  @PostMapping("/announcement")
  public PageVO<AnnouncementVO> queryAnnouncement(@RequestBody AnnouncementQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    checkPage(pageRequest);
    Page<AnnouncementPO> res = announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(
            query.getAffairIds(), query.getQuery(),
            pageRequest);
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

    suffix.setSuffix(query.getAllianceId().toString());
    Page<MaterialPO> byTagsIn = materialRepo
        .findByTagsIn(query.getTags().stream().map(VoAndPoConversion::toPO).collect(
            Collectors.toList()), query.getPageRequest());
    return new PageVO<>(byTagsIn, VoAndPoConversion::toVO);
  }

  @PostMapping("/affair")
  public PageVO<AffairVO> queryAffair(@RequestBody AffairQuery query) {
    checkPage(query.getPageRequest());

    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo.findAny(query.getQuery(), query.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
  }

  @PostMapping("/affair/tags")
  public PageVO<AffairVO> queryAffairTags(@RequestBody AffairQuery affairInfo) {
    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo
        .findByTagsIn(Lists.newArrayList(new TagPO(affairInfo.getQuery())),
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
    Page<AffairPO> page = affairRepo.findByName(affairInfo.getQuery(), affairInfo.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
  }

  @PostMapping("/role")
  public PageVO<RoleVO> queryRole(@RequestBody RoleQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    Page<RolePO> byAll = roleRepo.findByAll(query.getQuery());
    return new PageVO<>(byAll, VoAndPoConversion::toVO);
  }

  @PostMapping("/role/tags")
  public PageVO<RoleVO> queryRoleTags(@RequestBody RoleQuery query) {
    checkPage(query.getPageRequest());
    checkAllianceId(query.getAllianceId());

    suffix.setSuffix(query.getAllianceId().toString());
    List<TagPO> tagPOS = query.getTags().stream().map(VoAndPoConversion::toPO)
        .collect(Collectors.toList());
    Page<RolePO> byTagsIn = roleRepo.findByTagsIn(tagPOS, query.getPageRequest());
    return new PageVO<>(byTagsIn, VoAndPoConversion::toVO);
  }

  @PostMapping("/user")
  public List<UserVO> findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(@RequestBody String query) {
    return userService.findByUserNameOrEmailOrMobOrSuperIdOrTagsIn(query);
  }


  @GetMapping("/user/tags")
  public List<UserVO> queryUserByTag(@RequestParam String query) {
    return userService.findTop20ByTags(query);
  }

  @GetMapping("/user/username")
  public List<UserVO> queryUserByUsername(@RequestParam String query) {
    return userService.findTop20ByUserNameOrSuperId(query);
  }

  @GetMapping("/chat/date")
  public Page<ChatPO> queryChatDate(@RequestParam Date from, @RequestParam Date to) {
    return chatRepo.findAllByDateBetween(from, to, PageRequest.of(0, PAGE_SIZE));
  }

  @GetMapping("/chat/sender")
  public Page<ChatPO> queryChatSender(@RequestParam String sender) {
    return chatRepo.findAllBySender(sender, PageRequest.of(0, PAGE_SIZE));
  }

  @GetMapping("/chat/receiver")
  public Page<ChatPO> queryChatReceiver(@RequestParam String receiver) {
    return chatRepo.findAllByReceiver(receiver, PageRequest.of(0, PAGE_SIZE));
  }

  @GetMapping("/role/alliance")
  public Page<RolePO> queryAllianceRole(Long allianceId, @RequestParam String role) {
    return roleRepo.findRoleExcept(allianceId, role, PageRequest.of(0, PAGE_SIZE));
  }

  @GetMapping("/role/all")
  public Page<RolePO> queryAllRole(@RequestParam String role) {
    return roleRepo.findRoleInterAlliance(role, PageRequest.of(0, PAGE_SIZE));
  }
}
