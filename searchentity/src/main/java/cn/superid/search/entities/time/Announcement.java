package cn.superid.search.entities.time;

import cn.superid.search.entities.Tag;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldIndex;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

/**
 * Created by zzt on 17/5/27.
 */
@Document(indexName = "announcement", type = "announcement", refreshInterval = "1s")
public class Announcement {

    @Id @Field(type = FieldType.String, index = FieldIndex.not_analyzed)
    private String id;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String title;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String content;
    @Field(type = FieldType.Nested)
    private List<Tag> tags;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String modifierRole;
    @Field(type = FieldType.String, analyzer = "smartcn")
    private String modifierUser;

    public Announcement() {
    }

    public Announcement(String id, String title, String content, List<Tag> tags, String modifierRole, String modifierUser) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.tags = tags;
        this.modifierRole = modifierRole;
        this.modifierUser = modifierUser;
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

    public String getModifierUser() {
        return modifierUser;
    }

    public void setModifierUser(String modifierUser) {
        this.modifierUser = modifierUser;
    }

    public String getModifierRole() {
        return modifierRole;
    }

    public void setModifierRole(String modifierRole) {
        this.modifierRole = modifierRole;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Announcement{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", tags=" + tags +
                ", modifierRole='" + modifierRole + '\'' +
                ", modifierUser='" + modifierUser + '\'' +
                '}';
    }
}
