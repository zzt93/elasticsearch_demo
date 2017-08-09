package cn.superid.search.entities.user.file;

import cn.superid.search.entities.user.UserBasedIndex;

/**
 * Created by zzt on 17/5/27.
 */
public class FileSearchVO implements UserBasedIndex {

  private String id;
  private String name;
  private String uploadRoleId;
  private FileType type;

  private Long affairId;

  public FileSearchVO() {
  }

  public FileSearchVO(String id, Integer type) {
    this.id = id;
    this.type = FileType.values()[type];
  }

  public FileSearchVO(String id, String name) {
    this.id = id;
    this.name = name;
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

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public FileType getType() {
    return type;
  }

  public void setType(FileType type) {
    this.type = type;
  }

  public String indexSuffix() {
    return affairId.toString();
  }

  public static enum FileType {
    FILE, DIR,
  }
}

