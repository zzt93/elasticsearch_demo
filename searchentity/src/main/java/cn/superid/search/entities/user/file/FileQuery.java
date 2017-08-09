package cn.superid.search.entities.user.file;

import cn.superid.search.entities.StringQuery;

/**
 * @author zzt
 */
public class FileQuery extends StringQuery {

  private Long affairId;

  public FileQuery() {
  }

  public FileQuery(Long affairId, String query) {
    this.affairId = affairId;
    setQuery(query);
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }
}
