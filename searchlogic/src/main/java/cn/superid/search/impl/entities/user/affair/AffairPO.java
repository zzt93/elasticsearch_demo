package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/27.
 *
 * Suffix is allianceId
 *
 * @see AffairVO#indexSuffix()
 */
@Document(indexName = "affair-#{suffix.toString()}", type = "affair", refreshInterval = "1s", createIndex = false, shards = 1, replicas = 0)
public class AffairPO {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword, index = false)
  private String parentId;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String name;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;
  @Field(type = FieldType.keyword)
  private String superId;
  @Field(type = FieldType.Integer)
  private Integer state;
  @Field(type = FieldType.Integer)
  private Integer publicType;

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

  /**
   * This constructor is for test
   */
  AffairPO(String id, String name) {
    this.id = id;
    this.name = name;
    tags = Lists.newArrayList(new TagPO("test1"), new TagPO("test2"));
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

  public Integer getPublicType() {
    return publicType;
  }

  public AffairPO setPublicType(Integer publicType) {
    this.publicType = publicType;
    return this;
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
