package com.superid.query.user.role;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/21.
 */
@Document(indexName = "role", type = "role", refreshInterval = "1s", shards = 10)
public class Role {

    @Id
    private String id;

    @Field(type = FieldType.String, analyzer = "smartcn")
    private String title;
    @Field(type = FieldType.Boolean)
    private Boolean deprecated;
    @Field(type = FieldType.Long)
    private Long affairId;

    public Role() {
    }

    public Role(String id, String title, Boolean deprecated, Long affairId) {
        this.id = id;
        this.title = title;
        this.deprecated = deprecated;
        this.affairId = affairId;
    }

    public Long getAffairId() {
        return affairId;
    }

    public void setAffairId(Long affairId) {
        this.affairId = affairId;
    }

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

    public Boolean getDeprecated() {
        return deprecated;
    }

    public void setDeprecated(Boolean deprecated) {
        this.deprecated = deprecated;
    }
}
