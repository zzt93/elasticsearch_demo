package cn.superid.search.impl.entities;

import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.user.affair.AffairPO;
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
    return new AffairVO(po.getFatherId(), po.getId(), po.getState());
  }
}
