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
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "warehouse-#{suffix.toString()}", type = "material", createIndex = false)
public class MaterialPO {

  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.text, analyzer = "smartcn")
  private String title;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;

  public MaterialPO() {
  }

  public MaterialPO(MaterialVO entity) {
    id = entity.getId();
    title = entity.getTitle();
    tags = VoAndPoConversion.toPOs(entity.getTagVOS());
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<TagPO> getTags() {
    return tags;
  }

  public void setTags(List<TagPO> tags) {
    this.tags = tags;
  }

}
