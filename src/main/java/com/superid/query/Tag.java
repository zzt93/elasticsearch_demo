package com.superid.query;

import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

/**
 * Created by zzt on 17/6/6.
 */
public class Tag {

    @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String des;

    public Tag() {
    }

    public Tag(String des) {
        this.des = des;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String toString() {
        return des;
    }
}
