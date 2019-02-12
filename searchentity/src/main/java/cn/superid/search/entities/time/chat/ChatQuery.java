package cn.superid.search.entities.time.chat;

import cn.superid.search.entities.PagedQuery;
import java.util.List;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class ChatQuery extends PagedQuery {

  private Long startTime;
  private Long endTime;
  private String chatId;
  private Byte subType;
  private List<Byte> subTypes;

  public ChatQuery() {
  }

  public ChatQuery(String query, PageRequest pageRequest,
      String chatId, Byte subType) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.chatId = chatId;
    this.subType = subType;
  }

  public ChatQuery(String query, PageRequest pageRequest, Long startTime, Long endTime,
      String chatId, Byte subType) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.startTime = startTime;
    this.endTime = endTime;
    this.chatId = chatId;
    this.subType = subType;
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

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public Byte getSubType() {
    return subType;
  }

  public void setSubType(Byte subType) {
    this.subType = subType;
  }

  public List<Byte> getSubTypes() {
    return subTypes;
  }

  public void setSubTypes(List<Byte> subTypes) {
    this.subTypes = subTypes;
  }
}
