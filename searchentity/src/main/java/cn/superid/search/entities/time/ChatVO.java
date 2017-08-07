package cn.superid.search.entities.time;

import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by zzt on 17/6/5.
 */
public class ChatVO implements TimeBasedIndex {

  private String id;
  private Date date;
  private String sender;
  private String receiver;
  private String message;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getSender() {
    return sender;
  }

  public void setSender(String sender) {
    this.sender = sender;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String receiver) {
    this.receiver = receiver;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Timestamp getCreateTime() {
    return null;
  }

  public void setCreateTime(Timestamp createTime) {

  }

  public String indexSuffix() {
    return null;
  }
}
