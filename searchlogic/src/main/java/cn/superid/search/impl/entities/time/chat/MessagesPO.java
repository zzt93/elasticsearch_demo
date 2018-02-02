package cn.superid.search.impl.entities.time.chat;

import cn.superid.search.impl.entities.time.TimeBasedIndex;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "messages-#{suffix.toString()}", type = "messages", refreshInterval = "10s", createIndex = false, shards = 1)
public class MessagesPO implements TimeBasedIndex {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.Date)
  private long time;
  @Field(type = FieldType.keyword)
  private String content;
  @Field(type = FieldType.keyword)
  private String chatId;
  @Field(type = FieldType.Byte)
  private byte sub;

  public MessagesPO() {
  }

  public MessagesPO(String id, long time, String name, String content, String chatId, byte sub) {
    this.id = id;
    this.time = time;
    this.content = content;
    this.chatId = chatId;
    this.sub = sub;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getChatId() {
    return chatId;
  }

  public void setChatId(String chatId) {
    this.chatId = chatId;
  }

  public byte getSub() {
    return sub;
  }

  public void setSub(byte sub) {
    this.sub = sub;
  }

  @Override
  public String toString() {
    return "MessagesPO{" +
        "id='" + id + '\'' +
        ", time=" + time +
        ", content='" + content + '\'' +
        ", chatId=" + chatId +
        ", sub=" + sub +
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
