package cn.superid.search.impl.entities;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/6.
 */
public class TagPO {

  @Field(type = FieldType.keyword)
  private String des;

  public TagPO() {
  }

  public TagPO(String des) {
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
