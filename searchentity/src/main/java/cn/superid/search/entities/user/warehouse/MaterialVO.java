package cn.superid.search.entities.user.warehouse;

import cn.superid.search.entities.TagVO;
import cn.superid.search.entities.user.UserBasedIndex;
import java.util.List;

/**
 * Created by zzt on 17/6/27.
 */
public class MaterialVO implements UserBasedIndex {

  private String id;

  private String title;
  private List<TagVO> tagVOS;

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

  public List<TagVO> getTagVOS() {
    return tagVOS;
  }

  public void setTagVOS(List<TagVO> tagVOS) {
    this.tagVOS = tagVOS;
  }

  public String indexSuffix() {
    return null;
  }
}
