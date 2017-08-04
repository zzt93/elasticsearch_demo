package cn.superid.search.impl.save;

import cn.superid.common.notification.dto.NotificationMessage;
import cn.superid.common.notification.enums.PublishType;
import cn.superid.common.notification.enums.SearchType;
import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.time.Chat;
import cn.superid.search.entities.time.Task;
import cn.superid.search.entities.time.announcement.Announcement;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by zzt on 17/6/7.
 */
@Component
@EnableBinding({SaveSink.class})
public class MessageReceiver {

  private static final Logger logger = LoggerFactory.getLogger(MessageReceiver.class);
  private final UserRepo userRepo;
  private final ChatRepo chatRepo;
  private final FileRepo fileRepo;
  private final RoleRepo roleRepo;
  private final AnnouncementRepo announcementRepo;
  private final TaskRepo taskRepo;
  private final MaterialRepo materialRepo;
  private final AffairRepo affairRepo;
  private final ElasticsearchTemplate esTemplate;

  private final Suffix suffix;

  @Autowired
  public MessageReceiver(UserRepo userRepo, ChatRepo chatRepo, FileRepo fileRepo, RoleRepo roleRepo,
      AnnouncementRepo announcementRepo, TaskRepo taskRepo, MaterialRepo materialRepo,
      AffairRepo affairRepo, ElasticsearchTemplate esTemplate, SaveSink saveSink,
      Suffix suffix) {
    this.userRepo = userRepo;
    this.chatRepo = chatRepo;
    this.fileRepo = fileRepo;
    this.roleRepo = roleRepo;
    this.announcementRepo = announcementRepo;
    this.taskRepo = taskRepo;
    this.materialRepo = materialRepo;
    this.affairRepo = affairRepo;
    this.esTemplate = esTemplate;
    this.suffix = suffix;
  }

  @StreamListener(SaveSink.INPUT)
  public void process(Message<NotificationMessage> message) {
    // extract data
    NotificationMessage payload = message.getPayload();
    PublishType receiveType = payload.getType().getPublishType();
    if (receiveType != PublishType.SEARCH_INDEX) {
      return;
    }
    Map param = payload.getParam();
    Object data = param.get("data");
    RequestMethod verb = ((RequestMethod) param.get("verb"));

    // convert the data
    ObjectMapper mapper = new ObjectMapper();
    SearchType searchType = SearchType.valueOf(payload.getType().getDescription());
    RollingIndex entity = mapper.convertValue(data, searchType.getTargetClazz());

    // prepare index and mapping
    suffix.setSuffix(entity.indexSuffix());
    if (searchType != SearchType.AFFAIR) {
      createIfNotExists(searchType.getTargetClazz());
    }
    logger.debug("{}", entity);

    switch (searchType) {
      case FILE:
        fileRepo.save((File) entity);
        break;
      case ROLE:
        roleRepo.save((Role) entity);
        break;
      case USER:
        userRepo.save((User) entity);
        break;
      case AFFAIR:
        createIfNotExists(Affair.class);
        AffairNode node = (AffairNode) entity;
        Affair affair = new Affair(node.getId(), node.getName());
        affair.makePath(affairRepo.findById(node.getFatherId()).getPath());
        affairRepo.save(affair);
        break;
      case MATERIAL:
        materialRepo.save((Material) entity);
        break;

      // time-based repo

      case TASK:
        taskRepo.save((Task) entity);
        break;
      case CHAT:
        chatRepo.save((Chat) entity);
        break;
      case ANNOUNCEMENT:
        switch (verb) {
          case POST:
            announcementRepo.save((Announcement) entity);
            break;
          case DELETE:
            announcementRepo.delete(((Announcement) entity).getId());
            break;
          default:
            logger.error("Unsupported verb: {}", verb);
        }
        break;
    }
  }

  private void createIfNotExists(Class<?> aClass) {
    if (!esTemplate.indexExists(aClass)) {
      esTemplate.createIndex(aClass);
      esTemplate.putMapping(aClass);
    }
  }

}
