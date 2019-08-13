package cn.superid.search.impl.entities.user.affair;

import cn.superid.search.entities.user.affair.AffairVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
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
@Data
@Document(indexName = "affair-#{suffix.toString()}", type = "affair", refreshInterval = "1s", createIndex = false, shards = 1, replicas = 0)
public class AffairPO {

  public static final long CLUSTER_SIZE = 100000000;
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
  @Field(type = FieldType.Byte)
  private Byte mold;
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

  public AffairPO(String id, Long mold) {
    this.id = id;
    this.mold = mold.byteValue();
  }

}