package com.superid.query.dynamic.announcement;

import com.superid.query.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.util.List;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement", type = "announcement", refreshInterval = "1s")
@Setting(settingPath = "/cn-analyzer.json")
public class Announcement {

    @Id @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String title;
    @Field(type = FieldType.Nested)
    private List<Tag> tags;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String modifier;
    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String publisher;

    public Announcement() {
    }

    public Announcement(String id, String title, List<Tag> tags, String modifier, String publisher) {
        this.id = id;
        this.title = title;
        this.tags = tags;
        this.modifier = modifier;
        this.publisher = publisher;
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

    @Override
    public String toString() {
        return "Announcement{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", tags=" + tags +
                ", modifier='" + modifier + '\'' +
                ", publisher='" + publisher + '\'' +
                '}';
    }
}
