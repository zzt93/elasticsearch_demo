package com.superid.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.superid.MessageFormat;
import com.superid.MessageFormatReceiver;
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
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by zzt on 17/6/7.
 */
@Component
public class SaveReceiver extends MessageFormatReceiver {
    private static Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    private final UserRepo userRepo;
    private final ChatRepo chatRepo;
    private final FileRepo fileRepo;
    private final RoleRepo roleRepo;
    private final AnnouncementRepo announcementRepo;
    private final TaskRepo taskRepo;
    private final MaterialRepo materialRepo;
    private final AffairRepo affairRepo;
    private final ElasticsearchTemplate esTemplate;

    @Autowired
    public SaveReceiver(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, RoleRepo roleRepo, AnnouncementRepo announcementRepo, TaskRepo taskRepo, MaterialRepo materialRepo, AffairRepo affairRepo, ElasticsearchTemplate esTemplate) {
        this.userRepo = userRepo;
        this.chatRepo = chatRepo;
        this.fileRepo = fileRepo;
        this.roleRepo = roleRepo;
        this.announcementRepo = announcementRepo;
        this.taskRepo = taskRepo;
        this.materialRepo = materialRepo;
        this.affairRepo = affairRepo;
        this.esTemplate = esTemplate;
    }

    @Override
    public void receiveMessage(MessageFormat messageFormat) {
        System.out.println(messageFormat);

        SaveType saveType = SaveType.valueOf((String) messageFormat.getPayload("type"));
        String data = (String) messageFormat.getPayload("data");
        Object extra = messageFormat.getPayload("extra");
        if (!exists(saveType, extra)) {
            createNew(saveType, extra);
        }
        switch (saveType) {
            case FILE:
                fileRepo.save(gson.fromJson(data, File.class));
                break;
            case ROLE:
                roleRepo.save(gson.fromJson(data, Role.class));
                break;
            case USER:
                userRepo.save(gson.fromJson(data, User.class));
                break;
            case AFFAIR:
                AffairNode entity = gson.fromJson(data, AffairNode.class);
                Affair affair = new Affair(entity.getId(), entity.getName());
                affair.makePath(affairRepo.findById(entity.getFatherId()).getPath());
                affairRepo.save(affair);
                break;
            case MATERIAL:
                materialRepo.save(gson.fromJson(data, Material.class));
                break;

            // time-based repo
            case TASK:
                taskRepo.save(gson.fromJson(data, Task.class));
                break;
            case CHAT:
                chatRepo.save(gson.fromJson(data, Chat.class));
                break;
            case ANNOUNCEMENT:
                announcementRepo.save(gson.fromJson(data, Announcement.class));
                break;
        }
    }

    private boolean exists(SaveType saveType, Object extra) {
        return esTemplate.indexExists(saveType.indexName(extra));
    }

    private void createNew(SaveType saveType, Object extra) {
        String indexName = saveType.indexName(extra);
        esTemplate.createIndex(indexName, ElasticsearchTemplate.readFileFromClasspath("/templates/index-settings.json"));
        String type = saveType.name().toLowerCase();
        esTemplate.putMapping(indexName, type, ElasticsearchTemplate.readFileFromClasspath("/templates/" + type + ".json"));
    }
}
