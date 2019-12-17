package cn.superid.search.impl.mysql;

import cn.superid.search.impl.mysql.entity.RolePermission;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class AuthDaoImpl {

  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper<RolePermission> rolePermissionMapper = new BeanPropertyRowMapper<>(RolePermission.class);

  public List<RolePermission> roleLevelInAffair(List<Long> roles, List<Long> affairs) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("roles", roles);
    parameters.addValue("affairs", affairs);
    return jdbcTemplate.query(
        " select affair_id, role_id, r.permission_category from role_permission r join permission_identity p on r.use_identity and r.identity_id = p.id  where role_id in (:roles) and affair_id in (:affairs) ",
        rolePermissionMapper);
  }

  public List<RolePermission> roleLevelInAlliance(List<Long> roles, List<Long> alliances) {
    MapSqlParameterSource parameters = new MapSqlParameterSource();
    parameters.addValue("roles", roles);
    parameters.addValue("alliances", alliances);
    return jdbcTemplate.query(
        " select alliance_id, role_id, r.permission_category from role_permission r join permission_identity p on r.use_identity and r.identity_id = p.id where role_id in (:roles) and alliance_id in (:alliances) ",
        rolePermissionMapper);
  }

}
