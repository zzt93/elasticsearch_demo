package cn.superid.search.entities.user;

import java.sql.Timestamp;

/**
 * Created by zzt on 17/5/27.
 */
public class FileVO implements UserBasedIndex {

  private String id;
  private String name;
  private String uploadRole;

  private Timestamp modifyTime;
  private Double size;
  private Integer version;
  private Integer publicType;

  private Long allianceId;

  public FileVO() {
  }

  public FileVO(String id, String name, String uploadRole, Timestamp modifyTime, Double size,
      Integer version, Integer publicType, Long allianceId) {
    this.id = id;
    this.name = name;
    this.uploadRole = uploadRole;
    this.modifyTime = modifyTime;
    this.size = size;
    this.version = version;
    this.publicType = publicType;
    this.allianceId = allianceId;
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

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  public String indexSuffix() {
    return allianceId.toString();
  }
}

