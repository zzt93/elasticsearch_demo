package cn.superid.search.entities.user;

import cn.superid.search.entities.Tag;
import java.util.List;

/**
 * Created by zzt on 17/6/27.
 */
public class MaterialVO implements UserBasedIndex {

  private String id;

  private String title;
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

  public String indexSuffix() {
    return null;
  }
}
