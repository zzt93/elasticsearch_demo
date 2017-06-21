package com.superid.query.precreate.user;

import com.superid.query.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "user", type = "user", refreshInterval = "1s", shards = 10)
public class User {


    @Id
    private String id;
    private String realname;
    private String username;

    @Field(type = FieldType.Nested, index = FieldIndex.not_analyzed)
    private List<Tag> tags;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void setTags(List<Tag> tags) {
        this.tags = tags;
    }
}
