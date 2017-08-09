package cn.superid.search.impl.entities.user.file;

import cn.superid.search.entities.user.file.FileSearchVO;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
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

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String name;
  @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
  private String uploadRoleId;
  @Field(type = FieldType.Integer)
  private Integer type;

  public FilePO() {
  }

  public FilePO(FileSearchVO vo) {
    id = vo.getId();
    name = vo.getName();
    uploadRoleId = vo.getUploadRoleId();
    if (vo.getType() != null) {
      type = vo.getType().ordinal();
    }
  }

  public String getId() {
    return id;
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

