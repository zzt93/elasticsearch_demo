package cn.superid.search.impl.mysql.vo;

import lombok.Data;

/**
 * @author zzt
 */
@Data
public class RolePermissionVo {

  private Long roleId;
  private Long affairId;
  private Long allianceId;
  private Integer permissionLevel;

  public Long getContextId() {
    return affairId == null ? allianceId : affairId;
  }

  public String getContextIdName() {
    return affairId == null ? "allianceId" : "affairId";
  }
}
