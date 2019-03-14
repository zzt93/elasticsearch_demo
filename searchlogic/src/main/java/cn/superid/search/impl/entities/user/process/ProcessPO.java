package cn.superid.search.impl.entities.user.process;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.Arrays;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "process-#{suffix.toString()}", type = "process", createIndex = false, shards = 1, replicas = 0)
@Mapping(mappingPath = "mapping/process.json")
public class ProcessPO {
  public static final int CLUSTER_SIZE = 100000000;

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.Long)
  private Long templateId;
  @Field(type = FieldType.Byte)
  private Byte status;
  @Field(type = FieldType.Long)
  private Long roleId;
  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp time;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Byte)
  private Byte sourceType;
  @Field(type = FieldType.keyword)
  private String sourceName;
  @Field(type = FieldType.Long)
  private Long roleBelongedAffairId;
  @Field(type = FieldType.keyword)
  private String name;
  @Field(type = FieldType.keyword)
  private String[] tag;
  @Field(type = FieldType.Long)
  private long[] roles;
  @Field(type = FieldType.Long)
  private Long processBelongedAffairId;
  @Field(type = FieldType.Long)
  private Long sourceId;
  @Field(type = FieldType.Long)
  private Long serviceId;

  public ProcessPO() {
  }

  public ProcessPO(String id, Long templateId, Byte status, Long roleId, Timestamp time, Long affairId, Byte sourceType,
      String sourceName, Long roleBelongedAffairId, String name, String[] tag, long[] roles,
      Long processBelongedAffairId, Long sourceId, Long serviceId) {
    this.id = id;
    this.templateId = templateId;
    this.status = status;
    this.roleId = roleId;
    this.time = time;
    this.affairId = affairId;
    this.sourceType = sourceType;
    this.sourceName = sourceName;
    this.roleBelongedAffairId = roleBelongedAffairId;
    this.name = name;
    this.tag = tag;
    this.roles = roles;
    this.processBelongedAffairId = processBelongedAffairId;
    this.sourceId = sourceId;
    this.serviceId = serviceId;
  }

  public static int getClusterSize() {
    return CLUSTER_SIZE;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Long getTemplateId() {
    return templateId;
  }

  public void setTemplateId(Long templateId) {
    this.templateId = templateId;
  }

  public Byte getStatus() {
    return status;
  }

  public void setStatus(Byte status) {
    this.status = status;
  }

  public Long getRoleId() {
    return roleId;
  }

  public void setRoleId(Long roleId) {
    this.roleId = roleId;
  }

  public Timestamp getTime() {
    return time;
  }

  public void setTime(Timestamp time) {
    this.time = time;
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Byte getSourceType() {
    return sourceType;
  }

  public void setSourceType(Byte sourceType) {
    this.sourceType = sourceType;
  }

  public String getSourceName() {
    return sourceName;
  }

  public void setSourceName(String sourceName) {
    this.sourceName = sourceName;
  }

  public Long getRoleBelongedAffairId() {
    return roleBelongedAffairId;
  }

  public void setRoleBelongedAffairId(Long roleBelongedAffairId) {
    this.roleBelongedAffairId = roleBelongedAffairId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getTag() {
    return tag;
  }

  public void setTag(String[] tag) {
    this.tag = tag;
  }

  public long[] getRoles() {
    return roles;
  }

  public void setRoles(long[] roles) {
    this.roles = roles;
  }

  @Override
  public String toString() {
    return "ProcessPO{" +
        "id='" + id + '\'' +
        ", templateId=" + templateId +
        ", status=" + status +
        ", roleId=" + roleId +
        ", time=" + time +
        ", affairId=" + affairId +
        ", sourceType=" + sourceType +
        ", sourceName='" + sourceName + '\'' +
        ", roleBelongedAffairId=" + roleBelongedAffairId +
        ", name='" + name + '\'' +
        ", tag=" + Arrays.toString(tag) +
        ", roles=" + Arrays.toString(roles) +
        '}';
  }

  public Long getProcessBelongedAffairId() {
    return processBelongedAffairId;
  }

  public void setProcessBelongedAffairId(Long processBelongedAffairId) {
    this.processBelongedAffairId = processBelongedAffairId;
  }

  public Long getSourceId() {
    return sourceId;
  }

  public void setSourceId(Long sourceId) {
    this.sourceId = sourceId;
  }

  public Long getServiceId() {
    return serviceId;
  }

  public void setServiceId(Long serviceId) {
    this.serviceId = serviceId;
  }
}
