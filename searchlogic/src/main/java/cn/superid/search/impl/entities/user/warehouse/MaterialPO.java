package cn.superid.search.impl.entities.user.warehouse;

import cn.superid.search.entities.user.warehouse.MaterialVO;
import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * suffix is allianceId
 *
 * Created by zzt on 17/6/27.
 *
 * @see MaterialVO#indexSuffix()
 */
@Document(indexName = "material-#{suffix.toString()}", type = "material", createIndex = false, shards = 1, replicas = 0)
public class MaterialPO {

  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String name;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;
  @Field(type = FieldType.Long)
  private Long warehouseId;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Byte)
  private Byte publicType;


  public MaterialPO() {
  }

  MaterialPO(String id, String name,
      List<TagPO> tags, Long warehouseId, Long affairId, Byte type, Byte publicType) {
    this.id = id;
    this.name = name;
    this.tags = tags;
    this.warehouseId = warehouseId;
    this.affairId = affairId;
    this.type = type;
    this.publicType = publicType;
  }

  public MaterialPO(MaterialVO entity) {
    id = entity.getId();
    name = entity.getTitle();
    tags = VoAndPoConversion.toPOs(entity.getTagVOS());
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

  public Long getWarehouseId() {
    return warehouseId;
  }

  public void setWarehouseId(Long warehouseId) {
    this.warehouseId = warehouseId;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }

  public Byte getPublicType() {
    return publicType;
  }

  public void setPublicType(Byte publicType) {
    this.publicType = publicType;
  }
}
