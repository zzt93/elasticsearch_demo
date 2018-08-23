package cn.superid.search.impl.entities.user.target;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * @author zzt
 */
@Document(indexName = "target-#{suffix.toString()}", type = "target", createIndex = false, shards = 1, replicas = 0)
public class TargetPO {

  public static final int CLUSTER_SIZE = 1000000;
  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.keyword)
  private String name;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Byte)
  private Byte state;

  public TargetPO() {
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

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Byte getState() {
    return state;
  }

  public void setState(Byte state) {
    this.state = state;
  }
}
