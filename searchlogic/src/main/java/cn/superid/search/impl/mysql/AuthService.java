package cn.superid.search.impl.mysql;

import cn.superid.search.impl.mysql.entity.RolePermission;
import cn.superid.search.impl.mysql.vo.RolePermissionVo;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthService {

  private final AuthDaoImpl authDao;

  public List<RolePermissionVo> roleLevelInAffair(List<Long> roles, List<Long> affairs,
      int permissionCategory) {
    List<RolePermission> rolePermissions = authDao.roleLevelInAffair(roles, affairs);
    return getRolePermissionVos(rolePermissions, permissionCategory);
  }

  private List<RolePermissionVo> getRolePermissionVos(List<RolePermission> rolePermissions,
      int permissionCategory) {
    List<RolePermissionVo> res = new ArrayList<>(rolePermissions.size());
    for (RolePermission r : rolePermissions) {
      RolePermissionVo e = new RolePermissionVo();
      BeanUtils.copyProperties(r, e);
      e.setPermissionLevel(r.getPermissionCategory().get(permissionCategory).intValue());
      res.add(e);
    }
    return res;
  }

  public List<RolePermissionVo> roleLevelInAlliance(List<Long> roles, List<Long> alliances,
      int permissionCategory) {
    List<RolePermission> rolePermissions = authDao.roleLevelInAlliance(roles, alliances);
    return getRolePermissionVos(rolePermissions, permissionCategory);
  }

}
