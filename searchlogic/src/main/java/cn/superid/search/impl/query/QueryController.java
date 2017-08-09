package cn.superid.search.impl.query;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.Tag;
import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.user.affair.AffairQuery;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.file.FileQuery;
import cn.superid.search.entities.user.file.FileVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.entities.time.chat.ChatPO;
import cn.superid.search.impl.entities.time.chat.ChatRepo;
import cn.superid.search.impl.entities.time.task.TaskPO;
import cn.superid.search.impl.entities.time.task.TaskRepo;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.affair.AffairRepo;
import cn.superid.search.impl.entities.user.file.FileRepo;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import cn.superid.search.impl.entities.user.user.UserPO;
import cn.superid.search.impl.entities.user.user.UserRepo;
import cn.superid.search.impl.entities.user.warehouse.MaterialPO;
import cn.superid.search.impl.entities.user.warehouse.MaterialRepo;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.collect.Lists;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * The controller for query entities
 * Created by zzt on 17/6/6.
 */
@RestController
@RequestMapping("/query")
public class QueryController {

  private static final int PAGE_SIZE = 10;
  private final UserRepo userRepo;
  private final ChatRepo chatRepo;
  private final FileRepo fileRepo;
  private final RoleRepo roleRepo;
  private final AnnouncementRepo announcementRepo;
  private final TaskRepo taskRepo;
  private final AffairRepo affairRepo;
  private final MaterialRepo materialRepo;
  private final Suffix suffix;

  @Autowired
  public QueryController(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, RoleRepo roleRepo,
      AnnouncementRepo announcementRepo, TaskRepo taskRepo, AffairRepo affairRepo,
      MaterialRepo materialRepo, Suffix suffix) {
    this.userRepo = userRepo;
    this.chatRepo = chatRepo;
    this.fileRepo = fileRepo;
    this.roleRepo = roleRepo;
    this.announcementRepo = announcementRepo;
    this.taskRepo = taskRepo;
    this.affairRepo = affairRepo;
    this.materialRepo = materialRepo;
    this.suffix = suffix;
  }

  @PostMapping("/announcement")
  public PageVO<AnnouncementVO> queryAnnouncement(@RequestBody AnnouncementQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    if (checkPage(pageRequest.getPageNumber(), pageRequest.getPageSize())) {
      return null;
    }
    Page<AnnouncementPO> res = announcementRepo
        .findByTitleOrContentOrCreatorRoleOrCreatorUserOrAffairNameOrTagsInAffair(
            query.getAffairIds(), query.getQuery(),
            pageRequest);
    return new PageVO<>(res, VoAndPoConversion::toVO);
  }

  private boolean checkPage(@RequestParam int pageNum, @RequestParam int pageSize) {
    return (pageNum + 1) * pageSize > 1000;
  }

  @GetMapping("/task")
  public Page<TaskPO> queryTask(@RequestParam String query) {
    return taskRepo.findByTitle(query, new PageRequest(0, PAGE_SIZE));
  }

  @PostMapping("/file")
  public List<FileVO> queryFile(@RequestBody FileQuery query) {
    return fileRepo.findByTitleOrUploadRole(query.getQuery(), query.getAffairId());
  }

  @GetMapping("/material")
  public Page<MaterialPO> queryMaterial(@RequestParam String query) {
    return materialRepo.findByTitleOrTagsIn(query, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/user/mainAffair")
  public Page<UserPO> queryUser(@RequestParam Long affairId, @RequestParam String mainAffair) {
    return userRepo
        .findByAffairIdAndMainAffair(affairId, mainAffair, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/chat/date")
  public Page<ChatPO> queryChatDate(@RequestParam Date from, @RequestParam Date to) {
    return chatRepo.findAllByDateBetween(from, to, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/chat/sender")
  public Page<ChatPO> queryChatSender(@RequestParam String sender) {
    return chatRepo.findAllBySender(sender, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/chat/receiver")
  public Page<ChatPO> queryChatReceiver(@RequestParam String receiver) {
    return chatRepo.findAllByReceiver(receiver, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/role/alliance")
  public Page<RolePO> queryAllianceRole(Long allianceId, @RequestParam String role) {
    return roleRepo.findRoleExcept(allianceId, role, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/role/all")
  public Page<RolePO> queryAllRole(@RequestParam String role) {
    return roleRepo.findRoleInterAlliance(role, new PageRequest(0, PAGE_SIZE));
  }

  @PostMapping("/affair")
  public PageVO<AffairVO> queryAffair(@RequestBody AffairQuery affairInfo) {
    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo.findAny(affairInfo.getQuery(), affairInfo.getPageRequest());
    return new PageVO<>(page, VoAndPoConversion::toVO);
  }

  @PostMapping("/affair/tags")
  public PageVO<AffairVO> queryAffairTags(@RequestBody AffairQuery affairInfo) {
    suffix.setSuffix("*");
    Page<AffairPO> page = affairRepo
        .findByTagsIn(Lists.newArrayList(new Tag(affairInfo.getQuery())),
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
}
