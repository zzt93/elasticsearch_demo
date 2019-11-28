package cn.superid.search.impl.entities.wiki;

import cn.superid.search.entities.user.affair.AffairVO;
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
@Document(indexName = "wiki", type = "wiki", createIndex = false)
public class Wiki {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword)
  private String title;
  @Field(type = FieldType.keyword, index = false)
  private String content;

  public Wiki() {
  }


}