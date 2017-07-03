package com.superid.query;

import com.superid.query.time.announcement.Announcement;
import com.superid.query.time.announcement.AnnouncementRepo;
import com.superid.query.time.chat.Chat;
import com.superid.query.time.chat.ChatRepo;
import com.superid.query.time.task.Task;
import com.superid.query.time.task.TaskRepo;
import com.superid.query.user.affair.Affair;
import com.superid.query.user.affair.AffairRepo;
import com.superid.query.user.file.File;
import com.superid.query.user.file.FileRepo;
import com.superid.query.user.role.Role;
import com.superid.query.user.role.RoleRepo;
import com.superid.query.user.user.User;
import com.superid.query.user.user.UserRepo;
import com.superid.query.user.warehouse.Material;
import com.superid.query.user.warehouse.MaterialRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

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

    @Autowired
    public QueryController(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, RoleRepo roleRepo, AnnouncementRepo announcementRepo, TaskRepo taskRepo, AffairRepo affairRepo, MaterialRepo materialRepo) {
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
        this.fileRepo = fileRepo;
        this.roleRepo = roleRepo;
        this.announcementRepo = announcementRepo;
        this.taskRepo = taskRepo;
        this.affairRepo = affairRepo;
        this.materialRepo = materialRepo;
    }

    @GetMapping("/announcement")
    public Page<Announcement> queryAnnouncement(@RequestParam String query) {
        return announcementRepo.findAllByTitleOrPublisherOrModifierOrTagsIn(query, new PageRequest(0, PAGE_SIZE));
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
        return userRepo.findByAffairIdAndMainAffair(affairId, mainAffair, new PageRequest(0, PAGE_SIZE));
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
