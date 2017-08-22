package cn.superid.search.impl.entities.user.user;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.user.UserVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "user-#{suffix.toString()", type = "user", refreshInterval = "1s", shards = 10, createIndex = false)
public class UserPO {

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String username;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String superId;
  @Field(type = FieldType.Nested)
  private List<Tag> tags;


  public UserPO() {
  }

  public UserPO(UserVO vo) {
    id = vo.getId();
    username = vo.getUsername();
    tags = vo.getTags();
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

  public List<Tag> getTags() {
    return tags;
  }

  public void setTags(List<Tag> tags) {
    this.tags = tags;
  }

}
