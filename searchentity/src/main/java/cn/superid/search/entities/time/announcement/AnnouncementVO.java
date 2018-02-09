package cn.superid.search.entities.time.announcement;

/**
 * Created by zzt on 17/5/27.
 */
public class AnnouncementVO {

  private String id;
  private String title;
  private String thumbContent;
  private String content;

  public AnnouncementVO() {
  }

  public AnnouncementVO(String id, String title, String thumbContent, String content) {
    this.id = id;
    this.title = title;
    this.thumbContent = thumbContent;
    this.content = content;
  }

  public String getThumbContent() {
    return thumbContent;
  }

  public void setThumbContent(String thumbContent) {
    this.thumbContent = thumbContent;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

}
