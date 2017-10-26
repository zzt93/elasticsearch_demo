package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.user.user.UserVO;
import cn.superid.search.impl.entities.TagPO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
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
  @Field(type = FieldType.text, analyzer = "standard")
  private String username;
  @Field(type = FieldType.text, analyzer = "standard")
  private String email;
  @Field(type = FieldType.text, analyzer = "standard")
  private String mobile;
  @Field(type = FieldType.keyword)
  private String superId;
  @Field(type = FieldType.Nested)
  private List<TagPO> tags;


  public UserPO() {
  }

  UserPO(String id, String username, String superId,
      List<TagPO> tags) {
    this.id = id;
    this.username = username;
    this.superId = superId;
    this.tags = tags;
  }

  public UserPO(UserVO vo) {
    id = vo.getId();
    username = vo.getUsername();
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

  public List<TagPO> getTags() {
    return tags;
  }

  public void setTags(List<TagPO> tags) {
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
