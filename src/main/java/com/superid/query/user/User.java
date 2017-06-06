package com.superid.query.user;

import com.superid.query.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "user", type = "", refreshInterval = "1s")
public class User {


    @Id
    private String id;
    private String realname;
    private String username;

    @Field(type = FieldType.Nested)
    private List<Tag> tags;
}
