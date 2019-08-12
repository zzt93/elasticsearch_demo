package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.user.user.UserVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Data
@Document(indexName = "user", type = "user", refreshInterval = "1s", shards = 10)
public class UserPO {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.Long)
  private Long personalAffairId;

  @Field(type = FieldType.keyword)
  private String username;
  @Field(type = FieldType.keyword)
  private String email;
  @Field(type = FieldType.keyword)
  private String mobile;

  @Field(type = FieldType.keyword)
  private String superId;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.keyword)
  private String[] unionId;
  @Field(type = FieldType.Byte)
  private Integer publicType;
  @Field(type = FieldType.Short)
  private Short infoPublic;

  public UserPO() {
  }

  UserPO(String id, String username, String superId,
      String[] tags) {
    this.id = id;
    this.username = username;
    this.superId = superId;
    this.tags = tags;
  }

  public UserPO(UserVO vo) {
    id = vo.getId();
    username = vo.getUsername();
  }

  public Integer getPublicType() {
    return publicType;
  }

  public UserPO setPublicType(Integer publicType) {
    this.publicType = publicType;
    return this;
  }

  public String getSuperId() {
    return superId;
  }

}