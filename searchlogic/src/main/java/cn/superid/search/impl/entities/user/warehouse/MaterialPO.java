package cn.superid.search.impl.entities.user.warehouse;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.MaterialVO;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "warehouse", type = "material")
public class MaterialPO {

  @Id
  private String id;

  @Field(type = FieldType.String, analyzer = "smartcn")
  private String title;
  @Field(type = FieldType.Nested)
  private List<Tag> tags;

  public MaterialPO() {
  }

  public MaterialPO(MaterialVO entity) {
    id = entity.getId();
    title = entity.getTitle();
    tags = entity.getTags();
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

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

}
