package cn.superid.search.impl.entities;

import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.time.audit.AuditVO;
import cn.superid.search.entities.time.chat.MessagesVO;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.affair.AllianceVO;
import cn.superid.search.entities.user.file.FileSearchVO;
import cn.superid.search.entities.user.process.ProcessVO;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.target.TargetVO;
import cn.superid.search.entities.user.task.TaskVO;
import cn.superid.search.entities.user.user.InterestVO;
import cn.superid.search.entities.user.user.StudentVO;
import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.entities.user.warehouse.MaterialVO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.time.audit.AuditPO;
import cn.superid.search.impl.entities.time.chat.MessagesPO;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.file.FilePO;
import cn.superid.search.impl.entities.user.process.ProcessPO;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.target.TargetPO;
import cn.superid.search.impl.entities.user.task.TaskPO;
import cn.superid.search.impl.entities.user.user.UserPO;
import cn.superid.search.impl.entities.user.warehouse.MaterialPO;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

/**
 * @author zzt
 */
public class VoAndPoConversion {

  private static final Logger logger = LoggerFactory.getLogger(VoAndPoConversion.class);

  private static HashMap<Class<?>, Class> vo2po = new HashMap<>();

  static {
    vo2po.put(AffairVO.class, AffairPO.class);
    vo2po.put(AnnouncementVO.class, AnnouncementPO.class);
    vo2po.put(FileSearchVO.class, FilePO.class);
    vo2po.put(RoleVO.class, RolePO.class);
    vo2po.put(UserVO.class, UserPO.class);
    vo2po.put(MessagesVO.class, MaterialPO.class);
  }

  public static Class toPOClazz(Class<? extends RollingIndex> voClazz) {
    if (vo2po.containsKey(voClazz)) {
      return vo2po.get(voClazz);
    }
    throw new IllegalArgumentException();
  }

  public static AnnouncementVO toVO(AnnouncementPO po) {
    return new AnnouncementVO(po.getId(), po.getTitle(), po.getThumbContent());
  }

  public static AuditVO toVO(AuditPO po) {
    return new AuditVO(Long.parseLong(po.getId()));
  }

  public static TargetVO toVO(TargetPO po) {
    return new TargetVO(po.getId());
  }

  public static MaterialVO toVO(MaterialPO po) {
    return new MaterialVO(po.getId());
  }

  public static AffairVO toVO(AffairPO po) {
    AffairVO res = new AffairVO();
    BeanUtils.copyProperties(po, res);
    return res;
  }

  public static AllianceVO toAlliance(AffairPO affairPO) {
    return new AllianceVO(affairPO.getAllianceId());
  }


  public static FileSearchVO toVO(FilePO filePO) {
    return new FileSearchVO(filePO.voId(), filePO.getType());
  }


  public static UserVO toVO(UserPO userPO) {
    return new UserVO(userPO.getId());
  }

  public static StudentVO toStudentVO(UserPO userPO) {
    return new StudentVO(Long.parseLong(userPO.getId()), userPO.getPersonalAffairId(), userPO.getUnionId());
  }

  public static InterestVO toInterestVO(UserPO userPO) {
    return new InterestVO(Long.parseLong(userPO.getId()), userPO.getPersonalAffairId(), userPO.getTags());
  }

  public static String[] toPOs(String[] tagVOS) {
    return tagVOS;
  }

  public static RoleVO toVO(RolePO po) {
    return new RoleVO(po.getId());
  }

  public static MessagesVO toVO(MessagesPO messagesPO) {
    return new MessagesVO(messagesPO.getId());
  }

  public static TaskVO toVO(TaskPO taskPO) {
    return new TaskVO(taskPO.getId());
  }

  public static ProcessVO toVO(ProcessPO processPO) {
    return new ProcessVO(processPO.getId());
  }
}
