package cn.superid.search.impl.entities.user.role;

import cn.superid.search.entities.user.role.RoleVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/21.
 *
 * Suffix is allianceId
 *
 * @author zzt
 * @see cn.superid.search.impl.save.rolling.Suffix
 * @see RoleVO#indexSuffix()
 */
@Document(indexName = "role-#{suffix.toString()}", type = "role", createIndex = false, shards = 1, replicas = 0)
@Data
public class RolePO {

  public static final int CLUSTER_SIZE = 1000;
  @Id
  @JsonIgnore
  private String id;

  @Field(type = FieldType.keyword)
  private String title;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Long)
  private Long userId;
  @Field(type = FieldType.keyword)
  private String affairName;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Byte)
  private Byte publicType;
  @Field(type = FieldType.Long)
  private Long ownerRoleId;
  @Field(type = FieldType.keyword)
  private String ownerRoleTitle;
  @Field(type = FieldType.Long)
  private Long allianceId;
  private Map<String, Object> inTags;

  public RolePO() {
  }
  /**
   * for test
   */
  RolePO(String id, String title, Long affairId, Byte type,
      String[] t, Long ownerRoleId, long allianceId) {
    this.id = id;
    this.title = title;
    this.affairId = affairId;
    this.type = type;
    this.tags = t;
    this.ownerRoleId = ownerRoleId;
    this.allianceId = allianceId;
  }

}