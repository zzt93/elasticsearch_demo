package cn.superid.search.impl.entities;

import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.TagVO;
import cn.superid.search.entities.time.announcement.AnnouncementVO;
import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.entities.user.file.FileSearchVO;
import cn.superid.search.entities.user.role.RoleVO;
import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.entities.user.warehouse.MaterialVO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import cn.superid.search.impl.entities.user.affair.AffairPO;
import cn.superid.search.impl.entities.user.file.FilePO;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.user.UserPO;
import cn.superid.search.impl.entities.user.warehouse.MaterialPO;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
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
    return new AnnouncementVO(po.getId(), po.getTitle(), po.getContent(), toVOs(po.getTags()),
        po.getCreatorRole(), po.getCreatorRoleId(), po.getAffairId(),
        po.getAffairName(), po.getModifyTime(), null);
  }

  public static MaterialVO toVO(MaterialPO po) {
    return new MaterialVO(po.getId());
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

  public static TagPO toPO(TagVO tagVO) {
    return new TagPO(tagVO.getDes());
  }

  public static TagVO toVO(TagPO tagPO) {
    return new TagVO(tagPO.getDes());
  }

  public static List<TagPO> toPOs(List<TagVO> tagVOS) {
    return tagVOS.stream().map(VoAndPoConversion::toPO).collect(Collectors.toList());
  }

  public static List<TagVO> toVOs(List<TagPO> tagPOS) {
    return tagPOS.stream().map(VoAndPoConversion::toVO).collect(Collectors.toList());
  }

  public static RoleVO toVO(RolePO po) {
    return new RoleVO(po.getId());
  }
}
