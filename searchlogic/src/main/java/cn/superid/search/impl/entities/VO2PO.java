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
public class VO2PO {

  private static final Logger logger = LoggerFactory.getLogger(VO2PO.class);

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
}
