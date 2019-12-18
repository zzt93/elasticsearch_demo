package cn.superid.search.impl.entities;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.type.auth.PermissionLevel;
import cn.superid.search.entities.VisibleContext;
import cn.superid.search.entities.VisibleContext.UserInfo;
import cn.superid.search.impl.mysql.AuthService;
import cn.superid.search.impl.mysql.vo.RolePermissionVo;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class VisibleFilter {

  private final BusinessClient businessClient;
  private final AuthService authService;

  public BoolQueryBuilder get(VisibleContext context, int permissionCategory) {
    Preconditions.checkArgument(context != null, "No visibleContext");
    BoolQueryBuilder res = boolQuery();

    List<Long> affairs = context.getAffairs();
    List<Long> alliances = context.getAlliances();
    List<Long> roles = roles(context);
    List<RolePermissionVo> rolePermissionVos;
    if (affairs != null) {
      rolePermissionVos = authService.roleLevelInAffair(roles, affairs, permissionCategory);
    } else if (alliances != null) {
      rolePermissionVos = authService.roleLevelInAlliance(roles, alliances, permissionCategory);
    } else {
      throw new IllegalArgumentException("No context affairs & alliances");
    }

    String contextIdName = affairs != null ? "affairId" : "allianceId";
    for (RolePermissionVo vo : rolePermissionVos) {
      if (vo.getPermissionLevel() >= PermissionLevel.MEDIUM_LEVEL) {
        res.should(boolQuery().filter(termQuery(contextIdName, vo.getContextId())));
      } else {
        res.should(boolQuery().filter(termQuery(contextIdName, vo.getContextId())).filter(termsQuery("roles.role_id", roles)));
      }
    }

    return res;
  }

  private List<Long> roles(VisibleContext context) {
    Preconditions.checkArgument(context != null, "No visibleContext");
    Preconditions.checkArgument(context.getSelfInfo() != null, "No visibleContext.selfInfo");
    UserInfo userInfo = context.getSelfInfo();
    if (userInfo.getRoles() != null) {
      return userInfo.getRoles();
    }
    if (userInfo.getRole() != null) {
      return Collections.singletonList(userInfo.getRole());
    }
    Long userId = userInfo.getUserId();
    Preconditions.checkArgument(userId != null, "No visibleContext.userId");
    return Arrays.asList(businessClient.getRoleListOfUserList(Collections.singletonList(userId)).get(userId));
  }

}
