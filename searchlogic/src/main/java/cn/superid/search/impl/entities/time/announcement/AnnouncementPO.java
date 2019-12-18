package cn.superid.search.impl.entities.time.announcement;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.sql.Timestamp;
import java.util.Map;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/5/27.
 */
@Data
@Document(indexName = "announcement_-#{suffix.toString()}", type = "announcement", createIndex = false, shards = 1, replicas = 0)
public class AnnouncementPO {
  public static final int CLUSTER_SIZE = 10000;
  private String thumbContent;

  @Id
  @JsonIgnore
  private String id;
  @Field(type = FieldType.keyword)
  private String title;
  @Field(type = FieldType.text, analyzer = "ik_smart")
  private String[] content;
  @Field(type = FieldType.keyword)
  private String[] tags;
  @Field(type = FieldType.Long)
  private Long affairId;
  @Field(type = FieldType.Long)
  private Long allianceId;
  @Field(type = FieldType.Long)
  private Long targetId;
  @Field(type = FieldType.Date, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
  private Timestamp modifyTime;
  @Field(type = FieldType.Nested)
  private Map[] roles;
  @Field(type = FieldType.Byte)
  private Byte plateType;
  @Field(type = FieldType.Byte)
  private Byte type;
  @Field(type = FieldType.Integer)
  private Integer number;
  @Field(type = FieldType.Integer)
  private Integer plateSubType;

  public AnnouncementPO() {
  }

  /**
   * For test
   */
  public AnnouncementPO(String id, String title, String content, String[] tags,
      Map[] roles, Long affairId, Timestamp modifyTime, long allianceId) {
    this.id = id;
    this.title = title;
    this.content = new String[]{content};
    this.tags = tags;
    this.affairId = affairId;
    this.modifyTime = modifyTime;
    this.roles = roles;
    this.allianceId = allianceId;
  }

}