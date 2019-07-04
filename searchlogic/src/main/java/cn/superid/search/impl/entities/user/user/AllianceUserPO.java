package cn.superid.search.impl.entities.user.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "alliance_user", type = "alliance_user")
@Data
public class AllianceUserPO {

  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.Long)
  private long allianceId;
  @Field(type = FieldType.Long)
  private long userId;
  @Field(type = FieldType.keyword)
  private String username;


}