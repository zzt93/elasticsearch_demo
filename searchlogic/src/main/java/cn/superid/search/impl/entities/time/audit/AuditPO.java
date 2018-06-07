package cn.superid.search.impl.entities.time.audit;

import cn.superid.search.impl.entities.time.TimeBasedIndex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "audit-#{suffix.toString()}", type = "audit", refreshInterval = "10s", createIndex = false, shards = 1)
public class AuditPO implements TimeBasedIndex {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword)
  private String content;
  @Field(type = FieldType.Byte)
  private byte handleState;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.keyword)
  private String affairName;
  @Field(type = FieldType.Long)
  private Long userId;
  @Field(type = FieldType.keyword)
  private String username;
  @Field(type = FieldType.Long)
  private Long senderRoleId;
  @Field(type = FieldType.Long)
  private Long receiverRoleId;
  @Field(type = FieldType.keyword)
  private String senderTitle;
  @Field(type = FieldType.Date)
  private long time;

  public AuditPO() {
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public Long getReceiverRoleId() {
    return receiverRoleId;
  }

  public void setReceiverRoleId(Long receiverRoleId) {
    this.receiverRoleId = receiverRoleId;
  }

  public byte getHandleState() {
    return handleState;
  }

  public void setHandleState(byte handleState) {
    this.handleState = handleState;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getAffairName() {
    return affairName;
  }

  public void setAffairName(String affairName) {
    this.affairName = affairName;
  }

  public Long getSenderRoleId() {
    return senderRoleId;
  }

  public void setSenderRoleId(Long senderRoleId) {
    this.senderRoleId = senderRoleId;
  }

  public String getSenderTitle() {
    return senderTitle;
  }

  public void setSenderTitle(String senderTitle) {
    this.senderTitle = senderTitle;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "AuditPO{" +
        "id='" + id + '\'' +
        ", content='" + content + '\'' +
        ", handleState=" + handleState +
        ", affairId=" + affairId +
        ", userId=" + userId +
        ", username='" + username + '\'' +
        ", affairName='" + affairName + '\'' +
        ", senderRoleId=" + senderRoleId +
        ", senderTitle='" + senderTitle + '\'' +
        '}';
  }

  @Override
  public String indexSuffix() {
    return null;
  }

  public int timeFormatLen() {
    return "yyyy-MM".length();
  }

}
