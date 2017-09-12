package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "affair-#{suffix.toString()}", type = "affair", refreshInterval = "1s", createIndex = false)
public class AffairPO {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword, index = false)
  private String parentId;
  @Field(type = FieldType.text, analyzer = "smartcn")
  private String name;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;
  @Field(type = FieldType.keyword)
  private String superId;
  @Field(type = FieldType.Integer)
  private Integer state;

  public AffairPO() {
  }

  public AffairPO(AffairVO node) {
    id = node.getId();
    name = node.getName();
    parentId = node.getParentId();
    tags = VoAndPoConversion.toPOs(node.getTagVOS());
    superId = node.getSuperId();
    state = node.getState();
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

  public List<TagPO> getTags() {
    return tags;
  }

  public void setTags(List<TagPO> tags) {
    this.tags = tags;
  }

  public String getSuperId() {
    return superId;
  }

  public void setSuperId(String superId) {
    this.superId = superId;
  }

  public String getParentId() {
    return parentId;
  }

  public void setParentId(String parentId) {
    this.parentId = parentId;
  }

  public Integer getState() {
    return state;
  }

  public void setState(Integer state) {
    this.state = state;
  }

  @Override
  public String toString() {
    return "AffairPO{" +
        "id='" + id + '\'' +
        ", parentId='" + parentId + '\'' +
        ", name='" + name + '\'' +
        ", tags=" + tags +
        ", superId='" + superId + '\'' +
        '}';
  }
}
