package com.superid.query.user.warehouse;

import com.superid.query.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zzt on 17/6/27.
 */
@Document(indexName = "warehouse", type = "material")
public class Material {

    @Id
    private String id;

    @Field(type = FieldType.String, analyzer = "smartcn")
    private String title;
    @Field(type = FieldType.Nested)
    private List<Tag> tags;

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

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
