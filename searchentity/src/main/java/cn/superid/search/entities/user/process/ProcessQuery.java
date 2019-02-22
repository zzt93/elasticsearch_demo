package cn.superid.search.entities.user.process;

import cn.superid.search.entities.PagedQuery;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * The class encapsulates the query of {@link ProcessVO}
 *
 * @author zzt
 * @see ProcessVO
 */
public class ProcessQuery extends PagedQuery {

  private List<Long> affairIds;
  private List<Long> roleIds;
  private long startTime;
  private long endTime;
  private List<Byte> states;
  private List<Integer> templates;
  private QueryType queryType;
  public enum QueryType{
    TYPE_INNER,TYPE_OUTER,TYPE_CREATED;
  }

  public ProcessQuery() {
  }

  public ProcessQuery(List<Long> affairIds, List<Long> roleIds, long startTime, long endTime,
      List<Byte> states, List<Integer> templates, QueryType queryType) {
    this.affairIds = affairIds;
    this.roleIds = roleIds;
    this.startTime = startTime;
    this.endTime = endTime;
    this.states = states;
    this.templates = templates;
    this.queryType = queryType;
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
        ", roleIds=" + roleIds +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", states=" + states +
        ", templates=" + templates +
        ", queryType=" + queryType +
        '}';
  }
}
