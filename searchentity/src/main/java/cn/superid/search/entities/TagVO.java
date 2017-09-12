package cn.superid.search.entities;

/**
 * Created by zzt on 17/6/6.
 */
public class TagVO {

  private String des;

  public TagVO() {
  }

  public TagVO(String des) {
    this.des = des;
  }

  public String getDes() {
    return des;
  }

  public void setDes(String des) {
    this.des = des;
  }

  public String toString() {
    return des;
  }
}
