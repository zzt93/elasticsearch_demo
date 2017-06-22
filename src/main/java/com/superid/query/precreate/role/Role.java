package com.superid.query.precreate.role;

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

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String title;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String affair;
    @Field(type = FieldType.Boolean)
    private boolean deprecated;
    @Field(type = FieldType.Long)
    private long alliance;

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
}
