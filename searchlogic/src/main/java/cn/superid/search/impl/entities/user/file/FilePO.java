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
  static final int CLUSTER_SIZE = 500;
  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String name;
  @Field(type = FieldType.keyword)
  private String uploaderRoleId;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Byte)
  private Byte publicType;

  public FilePO() {
  }

  public FilePO(FileSearchVO vo) {
    if (vo.getType() == null) {
      return;
    }
    id = vo.getType().name() + SPLIT + vo.getId();
    name = vo.getName();
    uploaderRoleId = vo.getUploadRoleId();
    type = ((byte) vo.getType().ordinal());
  }


  public FilePO(String id, String name, String uploaderRoleId, Byte type) {
    this.id = id;
    this.name = name;
    this.uploaderRoleId = uploaderRoleId;
    this.type = type;
  }

  public Byte getPublicType() {
    return publicType;
  }

  public FilePO setPublicType(Byte publicType) {
    this.publicType = publicType;
    return this;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String voId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUploaderRoleId() {
    return uploaderRoleId;
  }

  public void setUploaderRoleId(String uploaderRoleId) {
    this.uploaderRoleId = uploaderRoleId;
  }

  public Byte getType() {
    return type;
  }

  public void setType(Byte type) {
    this.type = type;
  }
}
