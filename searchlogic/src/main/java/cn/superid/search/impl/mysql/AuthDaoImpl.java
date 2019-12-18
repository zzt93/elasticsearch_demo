package cn.superid.search.impl.mysql;

import cn.superid.search.impl.mysql.entity.RolePermission;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthDaoImpl {

  private final NamedParameterJdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper<RolePermission> rolePermissionMapper = new BeanPropertyRowMapper<>(RolePermission.class);

  public List<RolePermission> roleLevelInAffair(List<Long> roles, List<Long> affairs,
      int permissionCategory) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("roles", roles);
    parameters.addValue("affairs", affairs);
    return jdbcTemplate.query(
        " select affair_id, role_id, "
            + "IF(r.use_identity, json_extract(p.permission_category, '$[" + (permissionCategory-1) + "]'), json_extract(r.permission_category, '$[" + (permissionCategory-1) + "]')) mod 10 as permission_level "
            + "from role_permission r join permission_identity p on r.use_identity and r.identity_id = p.id where role_id in (:roles) and affair_id in (:affairs) ",
        parameters,
        rolePermissionMapper);
  }

  public List<RolePermission> roleLevelInAlliance(List<Long> roles, List<Long> alliances,
      int permissionCategory) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("roles", roles);
    parameters.addValue("alliances", alliances);
    return jdbcTemplate.query(
        " select alliance_id, role_id, "
            + "IF(r.use_identity, json_extract(p.permission_category, '$[" + (permissionCategory-1) + "]'), json_extract(r.permission_category, '$[" + (permissionCategory-1) + "]')) mod 10 as permission_level "
            + "from role_permission r join permission_identity p on r.use_identity and r.identity_id = p.id where role_id in (:roles) and alliance_id in (:alliances) ",
        parameters,
        rolePermissionMapper);
  }

}
