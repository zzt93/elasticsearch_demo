package cn.superid.search.impl.save;

import cn.superid.common.notification.dto.NotificationMessage;
import cn.superid.common.notification.enums.PublishType;
import cn.superid.common.notification.enums.SearchType;
import cn.superid.search.entities.time.Announcement;
import cn.superid.search.entities.time.Chat;
import cn.superid.search.entities.time.Task;
import cn.superid.search.entities.user.AffairNode;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * Created by zzt on 17/6/7.
 */
@Component
@EnableBinding({SaveSink.class})
public class SaveReceiver {

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
  public SaveReceiver(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, RoleRepo roleRepo,
      AnnouncementRepo announcementRepo, TaskRepo taskRepo, MaterialRepo materialRepo,
      AffairRepo affairRepo, ElasticsearchTemplate esTemplate, SaveSink saveSink) {
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

  @StreamListener(SaveSink.INPUT)
  public void process(Message<NotificationMessage> message) {
    NotificationMessage payload = message.getPayload();
    PublishType receiveType = payload.getType().getPublishType();
    if (receiveType != PublishType.SEARCH_INDEX) {
      return;
    }
    Map param = payload.getParam();
    Object data = param.get("data");
    ObjectMapper mapper = new ObjectMapper();
    SearchType searchType = SearchType.valueOf(payload.getType().getDescription());

    switch (searchType) {
      case FILE:
        fileRepo.save(mapper.convertValue(data, File.class));
        break;
      case ROLE:
        roleRepo.save(mapper.convertValue(data, Role.class));
        break;
      case USER:
        userRepo.save(mapper.convertValue(data, User.class));
        break;
      case AFFAIR:
        AffairNode entity = mapper.convertValue(data, AffairNode.class);
        Affair affair = new Affair(entity.getId(), entity.getName());
        affair.makePath(affairRepo.findById(entity.getFatherId()).getPath());
        affairRepo.save(affair);
        break;
      case MATERIAL:
        materialRepo.save(mapper.convertValue(data, Material.class));
        break;

      // time-based repo

      case TASK:
        taskRepo.save(mapper.convertValue(data, Task.class));
        break;
      case CHAT:
        chatRepo.save(mapper.convertValue(data, Chat.class));
        break;
      case ANNOUNCEMENT:
        announcementRepo.save(mapper.convertValue(data, Announcement.class));
        break;
    }
  }

  private boolean exists(SearchType searchType, Object extra) {
    return esTemplate.indexExists(searchType.indexName(extra));
  }

  private void createNew(SearchType searchType, Object extra) {
    String indexName = searchType.indexName(extra);
    esTemplate.createIndex(indexName,
        ElasticsearchTemplate.readFileFromClasspath("/templates/index-settings.json"));
    String type = searchType.name().toLowerCase();
    esTemplate.putMapping(indexName, type,
        ElasticsearchTemplate.readFileFromClasspath("/templates/" + type + ".json"));
  }
}
