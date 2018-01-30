package cn.superid.search.entities.time.chat;


/**
 * Created by zzt on 17/6/5.
 */
public class MessagesVO {

  private final String id;
  private final String content;

  public MessagesVO(String id, String content) {
    this.id = id;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public String getContent() {
    return content;
  }
}
