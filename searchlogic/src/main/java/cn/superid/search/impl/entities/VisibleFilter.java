package cn.superid.search.impl.entities;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import cn.superid.common.rest.client.BusinessClient;
import cn.superid.common.rest.type.PublicType;
import cn.superid.common.rest.type.auth.PermissionLevel;
import cn.superid.search.entities.VisibleContext;
import cn.superid.search.entities.VisibleContext.UserInfo;
import cn.superid.search.impl.mysql.AuthService;
import cn.superid.search.impl.mysql.vo.RolePermissionVo;
import cn.superid.search.impl.query.esUtil.QueryHelper;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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

  public BoolQueryBuilder filter(VisibleContext context, int permissionCategory) {
    Preconditions.checkArgument(context != null, "No visibleContext");
    List<Long> roles = roles(context), affairs = context.getAffairs(), alliances = context.getAlliances(), contextList;
    List<RolePermissionVo> rolePermissionVos;
    boolean isAffair = affairs != null;
    if (isAffair) {
      contextList = affairs;
      rolePermissionVos = authService.roleLevelInAffair(roles, affairs, permissionCategory);
    } else if (alliances != null) {
      contextList = alliances;
      rolePermissionVos = authService.roleLevelInAlliance(roles, alliances, permissionCategory);
    } else {
      throw new IllegalArgumentException("No context affairs & alliances");
    }
    List<Long> permissionList = rolePermissionVos.stream()
        .filter(v -> v.getPermissionLevel() >= PermissionLevel.MEDIUM_LEVEL)
        .map(v -> isAffair ? v.getAffairId() : v.getAllianceId()).collect(Collectors.toList());

    String contextIdName = isAffair ? "affairId" : "allianceId";

    return boolQuery()
        .should(boolQuery().filter(termQuery("publicType", PublicType.ALL)).filter(termsQuery(contextIdName, contextList)))
        .should(boolQuery().filter(QueryHelper.nestedRoleFilter(roles)).filter(termsQuery(contextIdName, contextList)))
        .should(termsQuery(contextIdName, permissionList));
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
