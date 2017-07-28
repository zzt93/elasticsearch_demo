package cn.superid.search.entities.user;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "file", type = "file", refreshInterval = "10s", shards = 10)
public class File implements UserBasedIndex {

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String title;
  private String content;

  @Field(type = FieldType.String, analyzer = "smartcn")
  private String uploadRole;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String uploadUser;

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

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getUploadUser() {
    return uploadUser;
  }

  public void setUploadUser(String uploadUser) {
    this.uploadUser = uploadUser;
  }

  public String getUploadRole() {
    return uploadRole;
  }

  public void setUploadRole(String uploadRole) {
    this.uploadRole = uploadRole;
  }

  public String indexSuffix() {
    return null;
  }
}

