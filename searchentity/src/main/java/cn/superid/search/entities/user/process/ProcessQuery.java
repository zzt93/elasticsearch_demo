package cn.superid.search.entities.user.process;

import cn.superid.search.entities.PagedQuery;
import java.util.List;

/**
 * The class encapsulates the query of {@link ProcessVO}
 *
 * @author zzt
 * @see ProcessVO
 */
public class ProcessQuery extends PagedQuery {

  private List<Long> affairIds;
  /**
   * 所属targetIds
   */
  private List<Long> targetIds;
  /**
   * 所属annIds
   */
  private List<Long> annIds;
  /**
   * 所查询的服务Ids
   */
  private List<Long> serviceIds;
  /**
   * 作为管理员的targetIds
   */
  private List<Long> adminTargetIds;
  /**
   * 作为官方的annIds
   */
  private List<Long> adminAnnIds;
  /**
   * 作为服务负责人的ServiceIds
   */
  private List<Long> adminServiceIds;
  private List<Long> roleIds;
  private long startTime;
  private long endTime;
  /**
   * service status
   */
  private List<Byte> states;
  /**
   * service type
   */
  private List<Integer> templates;
  private QueryType queryType;
  private Integer sourceType;
  private List<Long> processIds;

  public List<Long> getTargetIds() {
    return targetIds;
  }

  public void setTargetIds(List<Long> targetIds) {
    this.targetIds = targetIds;
  }

  public List<Long> getAnnIds() {
    return annIds;
  }

  public void setAnnIds(List<Long> annIds) {
    this.annIds = annIds;
  }

  public List<Long> getAdminTargetIds() {
    return adminTargetIds;
  }

  public void setAdminTargetIds(List<Long> adminTargetIds) {
    this.adminTargetIds = adminTargetIds;
  }

  public List<Long> getAdminAnnIds() {
    return adminAnnIds;
  }

  public void setAdminAnnIds(List<Long> adminAnnIds) {
    this.adminAnnIds = adminAnnIds;
  }

  public List<Long> getAdminServiceIds() {
    return adminServiceIds;
  }

  public void setAdminServiceIds(List<Long> adminServiceIds) {
    this.adminServiceIds = adminServiceIds;
  }

  public Integer getSourceType() {
    return sourceType;
  }

  public void setSourceType(Integer sourceType) {
    this.sourceType = sourceType;
  }

  public List<Long> getProcessIds() {
    return processIds;
  }

  public void setProcessIds(List<Long> processIds) {
    this.processIds = processIds;
  }

  public List<Long> getServiceIds() {
    return serviceIds;
  }

  public void setServiceIds(List<Long> serviceIds) {
    this.serviceIds = serviceIds;
  }

  public enum QueryType{
    TYPE_INNER,TYPE_OUTER,TYPE_ACT,TYPE_CREATED;
  }

  public ProcessQuery() {
  }

  public ProcessQuery(List<Long> affairIds, List<Long> targetIds, List<Long> annIds, List<Long> serviceIds,
      List<Long> adminTargetIds, List<Long> adminAnnIds, List<Long> adminServiceIds, List<Long> roleIds, long startTime,
      long endTime, List<Byte> states, List<Integer> templates,
      QueryType queryType, Integer sourceType, List<Long> processIds) {
    this.affairIds = affairIds;
    this.targetIds = targetIds;
    this.annIds = annIds;
    this.serviceIds = serviceIds;
    this.adminTargetIds = adminTargetIds;
    this.adminAnnIds = adminAnnIds;
    this.adminServiceIds = adminServiceIds;
    this.roleIds = roleIds;
    this.startTime = startTime;
    this.endTime = endTime;
    this.states = states;
    this.templates = templates;
    this.queryType = queryType;
    this.sourceType = sourceType;
    this.processIds = processIds;
  }

  public List<Long> getAffairIds() {
    return affairIds;
  }

  public void setAffairIds(List<Long> affairIds) {
    this.affairIds = affairIds;
  }

  public List<Long> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(List<Long> roleIds) {
    this.roleIds = roleIds;
  }

  public long getStartTime() {
    return startTime;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public List<Byte> getStates() {
    return states;
  }

  public void setStates(List<Byte> states) {
    this.states = states;
  }

  public List<Integer> getTemplates() {
    return templates;
  }

  public void setTemplates(List<Integer> templates) {
    this.templates = templates;
  }

  public QueryType getQueryType() {
    return queryType;
  }

  public void setQueryType(QueryType queryType) {
    this.queryType = queryType;
  }

  @Override
  public String toString() {
    return "ProcessQuery{" +
        "affairIds=" + affairIds +
        ", targetIds=" + targetIds +
        ", annIds=" + annIds +
        ", serviceIds=" + serviceIds +
        ", adminTargetIds=" + adminTargetIds +
        ", adminAnnIds=" + adminAnnIds +
        ", adminServiceIds=" + adminServiceIds +
        ", roleIds=" + roleIds +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", states=" + states +
        ", templates=" + templates +
        ", queryType=" + queryType +
        ", sourceType=" + sourceType +
        ", processIds=" + processIds +
        '}';
  }
}
