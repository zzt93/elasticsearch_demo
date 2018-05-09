package cn.superid.search.entities.user.task;

/**
 * Created by zzt on 17/6/21.
 */
public class TaskVO {

  private String id;
  private String title;

  public TaskVO() {
  }

  public TaskVO(String id) {
    this.id = id;
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

}
