package cn.superid.search.impl.save;

import cn.superid.common.notification.dto.NotificationMessage;
import cn.superid.common.notification.enums.PublishType;
import cn.superid.common.notification.enums.SearchType;
import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.time.ChatVO;
import cn.superid.search.entities.time.TaskVO;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.user.AffairVO;
import cn.superid.search.entities.user.FileVO;
import cn.superid.search.entities.user.MaterialVO;
import cn.superid.search.entities.user.RoleVO;
import cn.superid.search.entities.user.UserVO;
import cn.superid.search.impl.query.time.announcement.AnnouncementPO;
import cn.superid.search.impl.query.time.announcement.AnnouncementRepo;
import cn.superid.search.impl.query.time.chat.ChatPO;
import cn.superid.search.impl.query.time.chat.ChatRepo;
import cn.superid.search.impl.query.time.task.TaskPO;
import cn.superid.search.impl.query.time.task.TaskRepo;
import cn.superid.search.impl.query.user.affair.AffairPO;
import cn.superid.search.impl.query.user.affair.AffairRepo;
import cn.superid.search.impl.query.user.file.FilePO;
import cn.superid.search.impl.query.user.file.FileRepo;
import cn.superid.search.impl.query.user.role.RolePO;
import cn.superid.search.impl.query.user.role.RoleRepo;
import cn.superid.search.impl.query.user.user.UserPO;
import cn.superid.search.impl.query.user.user.UserRepo;
import cn.superid.search.impl.query.user.warehouse.MaterialPO;
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
        fileRepo.save(new FilePO((FileVO) entity));
        break;
      case ROLE:
        roleRepo.save(new RolePO((RoleVO) entity));
        break;
      case USER:
        userRepo.save(new UserPO((UserVO) entity));
        break;
      case AFFAIR:
        createIfNotExists(AffairPO.class);
        AffairVO node = (AffairVO) entity;
        AffairPO affairPO = new AffairPO(node.getId(), node.getName());
        affairPO.makePath(affairRepo.findById(node.getFatherId()).getPath());
        affairRepo.save(affairPO);
        break;
      case MATERIAL:
        materialRepo.save(new MaterialPO((MaterialVO) entity));
        break;

      // time-based repo

      case TASK:
        taskRepo.save(new TaskPO((TaskVO) entity));
        break;
      case CHAT:
        chatRepo.save(new ChatPO((ChatVO) entity));
        break;
      case ANNOUNCEMENT:
        switch (verb) {
          case POST:
            announcementRepo.save(new AnnouncementPO((AnnouncementVO) entity));
            break;
          case DELETE:
            announcementRepo.delete(((AnnouncementVO) entity).getId());
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
