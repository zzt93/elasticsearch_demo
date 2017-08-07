package cn.superid.search.impl.entities.user.file;

import cn.superid.search.entities.user.FileVO;
import java.sql.Timestamp;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "file-#{suffix.toString()}", type = "file", refreshInterval = "10s", shards = 10)
public class FilePO {

  @Id
  private String id;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String name;
  @Field(type = FieldType.String, analyzer = "smartcn")
  private String uploadRole;

  @Field(type = FieldType.Date, index = FieldIndex.no, pattern = "YYYY-MM-DD HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Double, index = FieldIndex.no)
  private Double size;
  @Field(type = FieldType.Integer, index = FieldIndex.no)
  private Integer version;
  @Field(type = FieldType.Integer, index = FieldIndex.no)
  private Integer publicType;


  public FilePO() {
  }

  public FilePO(String id, String name, String uploadRole, Timestamp modifyTime, Double size,
      Integer version, Integer publicType, Long allianceId) {
    this.id = id;
    this.name = name;
    this.uploadRole = uploadRole;
    this.modifyTime = modifyTime;
    this.size = size;
    this.version = version;
    this.publicType = publicType;
  }

  public FilePO(FileVO vo) {
    id = vo.getId();
    name = vo.getName();
    uploadRole = vo.getUploadRole();
    modifyTime = vo.getModifyTime();
    size = vo.getSize();
    version = vo.getVersion();
    publicType = vo.getPublicType();
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

  public String getUploadRole() {
    return uploadRole;
  }

  public void setUploadRole(String uploadRole) {
    this.uploadRole = uploadRole;
  }

  public Timestamp getModifyTime() {
    return modifyTime;
  }

  public void setModifyTime(Timestamp modifyTime) {
    this.modifyTime = modifyTime;
  }

  public Double getSize() {
    return size;
  }

  public void setSize(Double size) {
    this.size = size;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Integer getPublicType() {
    return publicType;
  }

  public void setPublicType(Integer publicType) {
    this.publicType = publicType;
  }

}

