package cn.superid.search.impl.query.user.user;

import cn.superid.search.entities.Tag;
import cn.superid.search.entities.user.UserVO;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "user", type = "user", refreshInterval = "1s", shards = 10)
public class UserPO {

  @Id
  private String id;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String realname;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String username;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String role;

  @Field(type = FieldType.Nested)
  private List<Tag> tags;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String mainAffair;

  public UserPO() {
  }

  public UserPO(UserVO vo) {
    id = vo.getId();
    realname = vo.getRealname();
    username = vo.getUsername();
    role = vo.getRole();
    tags = vo.getTags();
    affairId = vo.getAffairId();
    mainAffair = vo.getMainAffair();
  }

  public String getMainAffair() {
    return mainAffair;
  }

  public void setMainAffair(String mainAffair) {
    this.mainAffair = mainAffair;
  }

  public String getRole() {
    return role;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getRealname() {
    return realname;
  }

  public void setRealname(String realname) {
    this.realname = realname;
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
