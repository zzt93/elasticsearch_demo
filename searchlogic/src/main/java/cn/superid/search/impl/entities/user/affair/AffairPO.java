package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.affair.AffairVO;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "affair", type = "affair", refreshInterval = "1s")
public class AffairPO {

  @Id
  private String id;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String fatherId;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String name;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String path;
  @Field(type = FieldType.Nested)
  private List<Tag> tags;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String superId;

  public AffairPO() {
  }

  public AffairPO(AffairVO node) {
    id = node.getId();
    name = node.getName();
    fatherId = node.getFatherId();
    tags = node.getTags();
    superId = node.getSuperId();
  }

  AffairPO(String id, String name) {
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

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

  public String getSuperId() {
    return superId;
  }

  public void setSuperId(String superId) {
    this.superId = superId;
  }

  public String getFatherId() {
    return fatherId;
  }

  public void setFatherId(String fatherId) {
    this.fatherId = fatherId;
  }

  @Override
  public String toString() {
    return "AffairPO{" +
        "id='" + id + '\'' +
        ", fatherId='" + fatherId + '\'' +
        ", name='" + name + '\'' +
        ", path='" + path + '\'' +
        ", tags=" + tags +
        ", superId='" + superId + '\'' +
        '}';
  }
}
