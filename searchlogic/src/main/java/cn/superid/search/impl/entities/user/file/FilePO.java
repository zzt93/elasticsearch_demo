package cn.superid.search.impl.entities.user.file;

import cn.superid.search.entities.user.file.FileSearchVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Suffix is affairId
 *
 * Created by zzt on 17/5/27.
 *
 * @see FileSearchVO#indexSuffix()
 * @see cn.superid.search.impl.save.rolling.Suffix
 */
@Document(indexName = "file-#{suffix.toString()}", type = "file", refreshInterval = "10s", shards = 1, createIndex = false)
public class FilePO {

  private static final String SPLIT = "#";
  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.text, analyzer = "smartcn")
  private String name;
  @Field(type = FieldType.keyword)
  private String uploadRoleId;
  @Field(type = FieldType.Integer)
  private Integer type;

  public FilePO() {
  }

  public FilePO(FileSearchVO vo) {
    if (vo.getType() == null) {
      return;
    }
    id = vo.getType().name() + SPLIT + vo.getId();
    name = vo.getName();
    uploadRoleId = vo.getUploadRoleId();
    type = vo.getType().ordinal();
  }

  public FilePO(String id, String name, String uploadRoleId, Integer type) {
    this.id = id;
    this.name = name;
    this.uploadRoleId = uploadRoleId;
    this.type = type;
  }

  public String getId() {
    return id;
  }

  public String voId() {
    return id.split(SPLIT)[1];
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUploadRoleId() {
    return uploadRoleId;
  }

  public void setUploadRoleId(String uploadRoleId) {
    this.uploadRoleId = uploadRoleId;
  }

  public Integer getType() {
    return type;
  }

  public void setType(Integer type) {
    this.type = type;
  }
}

