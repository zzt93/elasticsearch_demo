package com.superid.query.time.chat;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * Created by zzt on 17/6/5.
 */
@Document(indexName = "chat", type = "chat", refreshInterval = "10s")
public class Chat {

    @Id
    private String id;
    @Field(type = FieldType.Date, format = DateFormat.date)
    private Date date;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String sender;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String receiver;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
