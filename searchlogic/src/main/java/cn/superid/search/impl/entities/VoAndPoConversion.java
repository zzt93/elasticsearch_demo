package cn.superid.search.impl.entities;

import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.file.FileSearchVO;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.file.FilePO;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.user.UserPO;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zzt
 */
public class VoAndPoConversion {

  private static final Logger logger = LoggerFactory.getLogger(VoAndPoConversion.class);

  private static HashMap<Class<? extends RollingIndex>, Class> vo2po = new HashMap<>();

  static {
    vo2po.put(AffairVO.class, AffairPO.class);
    vo2po.put(AnnouncementVO.class, AnnouncementPO.class);
    vo2po.put(FileSearchVO.class, FilePO.class);
    vo2po.put(RoleVO.class, RolePO.class);
    vo2po.put(UserVO.class, UserPO.class);
  }

  public static Class toPOClazz(Class<? extends RollingIndex> voClazz) {
    if (vo2po.containsKey(voClazz)) {
      return vo2po.get(voClazz);
    }
    throw new IllegalArgumentException();
  }

  public static AnnouncementVO toVO(AnnouncementPO po) {
    return new AnnouncementVO(po.getId(), po.getTitle(), po.getContent(), po.getTags(),
        po.getCreatorRole(), po.getCreatorUser(), po.getCreatorRoleId(), po.getCreatorRoleId(),
        po.getAffairName(), po.getModifyTime(), po.getCreatorUserId(), po.getTop(), po.getType(),
        po.getEntityMap(), po.getAvatar(), null);
  }

  public static AffairVO toVO(AffairPO po) {
    return new AffairVO(po.getParentId(), po.getId(), po.getState());
  }


  public static FileSearchVO toVO(FilePO filePO) {
    return new FileSearchVO(filePO.voId(), filePO.getType());
  }


  public static UserVO toVO(UserPO userPO) {
    return new UserVO(userPO.getId());
  }
}
