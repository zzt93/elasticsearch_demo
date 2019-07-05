package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Arrays;
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
  @Field(type = FieldType.keyword)
  private String name;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.keyword)
  private String superId;
  @Field(type = FieldType.Byte)
  private Byte state;
  @Field(type = FieldType.Byte)
  private Byte publicType;
  @Field(type = FieldType.Long)
  private Long allianceId;

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
    tags = new String[]{"test1", "test2"};
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

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
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

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }

  public Byte getPublicType() {
    return publicType;
  }

  public AffairPO setPublicType(Byte publicType) {
    this.publicType = publicType;
    return this;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  @Override
  public String toString() {
    return "AffairPO{" +
        "id='" + id + '\'' +
        ", parentId='" + parentId + '\'' +
        ", name='" + name + '\'' +
        ", tags=" + Arrays.toString(tags) +
        ", superId='" + superId + '\'' +
        '}';
  }
}