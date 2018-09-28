package cn.superid.search.entities.time.chat;

import cn.superid.search.entities.PagedQuery;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class ChatIdsQuery extends PagedQuery {

  private Long startTime;
  private Long endTime;
  private List<String> chatIds;
  private Byte subType;

  public ChatIdsQuery() {
  }

  public ChatIdsQuery(String query,
      List<String> chatIds, Byte subType, PageRequest pageRequest) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.subType = subType;
    this.chatIds = chatIds;
  }

  public ChatIdsQuery(String query, Long startTime, Long endTime,
      List<String> chatIds, Byte subType, PageRequest pageRequest) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.startTime = startTime;
    this.endTime = endTime;
    this.subType = subType;
    this.chatIds = chatIds;
  }

  public List<String> getChatIds() {
    return chatIds;
  }

  public ChatIdsQuery setChatIds(List<String> chatIds) {
    this.chatIds = chatIds;
    return this;
  }

  public Long getStartTime() {
    return startTime;
  }

  public void setStartTime(Long startTime) {
    this.startTime = startTime;
  }

  public Long getEndTime() {
    return endTime;
  }

  public void setEndTime(Long endTime) {
    this.endTime = endTime;
  }

  public Byte getSubType() {
    return subType;
  }

  public void setSubType(Byte subType) {
    this.subType = subType;
  }
}
