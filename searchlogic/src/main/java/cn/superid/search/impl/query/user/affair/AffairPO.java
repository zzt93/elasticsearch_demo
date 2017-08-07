package cn.superid.search.impl.query.user.affair;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "affair", type = "affair", refreshInterval = "1s")
public class AffairPO {

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String name;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String path;

  public AffairPO() {
  }

  public AffairPO(String id, String name) {
    this.id = id;
    this.name = name;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public AffairPO makePath(String father) {
    this.path = father + " " + name;
    return this;
  }

  @Override
  public String toString() {
    return "AffairPO{" +
        "id='" + id + '\'' +
        ", name='" + name + '\'' +
        ", path='" + path + '\'' +
        '}';
  }
}
