package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.user.user.UserVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "user", type = "user", refreshInterval = "1s", shards = 10)
public class UserPO {

  @Id
  @JsonIgnore
  private String id;

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
  @Field(type = FieldType.Byte)
  private Integer publicType;

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

  public void setSuperId(String superId) {
    this.superId = superId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String[] getTags() {
    return tags;
  }

  public void setTags(String[] tags) {
    this.tags = tags;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
}