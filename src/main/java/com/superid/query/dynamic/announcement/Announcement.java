package com.superid.query.dynamic.announcement;

import com.superid.query.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement", type = "announcement", refreshInterval = "1s", createIndex = false)
public class Announcement {

    @Id
    private String id;
    private String title;
    @Field(type = FieldType.Nested, index = FieldIndex.not_analyzed)
    private List<Tag> tags;
    @Field(index = FieldIndex.not_analyzed)
    private String modifier;
    @Field(index = FieldIndex.not_analyzed)
    private String publisher;

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

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getModifier() {
        return modifier;
    }

    public void setModifier(String modifier) {
        this.modifier = modifier;
    }
}
