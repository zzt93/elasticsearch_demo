package cn.superid.search.entities.time.chat;

import cn.superid.search.entities.PagedQuery;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class ChatQuery extends PagedQuery {

  private long startTime;
  private long endTime;
  private String chatId;
  private Byte subType;

  public ChatQuery() {
  }

  public ChatQuery(String query, PageRequest pageRequest, long startTime, long endTime, String chatId, byte subType) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.startTime = startTime;
    this.endTime = endTime;
    this.chatId = chatId;
    this.subType = subType;
  }

  public long getStartTime() {
    return startTime;
  }

  public long getEndTime() {
    return endTime;
  }

  public String getChatId() {
    return chatId;
  }

  public Byte getSubType() {
    return subType;
  }

  public void setStartTime(long startTime) {
    this.startTime = startTime;
  }

  public void setEndTime(long endTime) {
    this.endTime = endTime;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public void setSubType(byte subType) {
    this.subType = subType;
  }
}
