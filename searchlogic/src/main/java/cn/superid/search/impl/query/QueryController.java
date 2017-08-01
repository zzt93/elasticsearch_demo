package cn.superid.search.impl.query;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.time.Chat;
import cn.superid.search.entities.time.Task;
import cn.superid.search.entities.time.announcement.Announcement;
import cn.superid.search.entities.time.announcement.AnnouncementQuery;
import cn.superid.search.entities.user.File;
import cn.superid.search.entities.user.Material;
import cn.superid.search.entities.user.Role;
import cn.superid.search.entities.user.User;
import cn.superid.search.impl.query.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.query.time.chat.ChatRepo;
import cn.superid.search.impl.query.time.task.TaskRepo;
import cn.superid.search.impl.query.user.affair.Affair;
import cn.superid.search.impl.query.user.affair.AffairRepo;
import cn.superid.search.impl.query.user.file.FileRepo;
import cn.superid.search.impl.query.user.role.RoleRepo;
import cn.superid.search.impl.query.user.user.UserRepo;
import cn.superid.search.impl.query.user.warehouse.MaterialRepo;
import cn.superid.search.impl.save.Suffix;
import java.util.Date;
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
  public PageVO<Announcement> queryAnnouncement(@RequestBody AnnouncementQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    if (checkPage(pageRequest.getPageNumber(), pageRequest.getPageSize())) {
      return null;
    }
    Page<Announcement> res = announcementRepo
        .findByTitleOrModifierRoleOrModifierUserOrTagsIn(query.getQuery(), query.getAffairIds(),
            pageRequest);
    return new PageVO<>(res);
  }

  private boolean checkPage(@RequestParam int pageNum, @RequestParam int pageSize) {
    return (pageNum + 1) * pageSize > 1000;
  }

  @GetMapping("/task")
  public Page<Task> queryTask(@RequestParam String query) {
    return taskRepo.findByTitle(query, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/file")
  public Page<File> queryFile(@RequestParam String query) {
    return fileRepo.findByTitleOrUploadRole(query, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/material")
  public Page<Material> queryMaterial(@RequestParam String query) {
    return materialRepo.findByTitleOrTagsIn(query, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/user/mainAffair")
  public Page<User> queryUser(@RequestParam Long affairId, @RequestParam String mainAffair) {
    return userRepo
        .findByAffairIdAndMainAffair(affairId, mainAffair, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/chat/date")
  public Page<Chat> queryChatDate(@RequestParam Date from, @RequestParam Date to) {
    return chatRepo.findAllByDateBetween(from, to, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/chat/sender")
  public Page<Chat> queryChatSender(@RequestParam String sender) {
    return chatRepo.findAllBySender(sender, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/chat/receiver")
  public Page<Chat> queryChatReceiver(@RequestParam String receiver) {
    return chatRepo.findAllByReceiver(receiver, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/role/alliance")
  public Page<Role> queryAllianceRole(Long allianceId, @RequestParam String role) {
    return roleRepo.findRoleExcept(allianceId, role, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("/role/all")
  public Page<Role> queryAllRole(@RequestParam String role) {
    return roleRepo.findRoleInterAlliance(role, new PageRequest(0, PAGE_SIZE));
  }

  @GetMapping("affair")
  public Page<Affair> queryAffair(@RequestParam String affairInfo) {
    return affairRepo.findByNameOrPath(affairInfo, new PageRequest(0, PAGE_SIZE));
  }
}
