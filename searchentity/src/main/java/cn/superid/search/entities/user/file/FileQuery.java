package cn.superid.search.entities.user.file;

import cn.superid.search.entities.StringQuery;

/**
 * @author zzt
 */
public class FileQuery extends StringQuery {

  private Long allianceId;
  private Long affairId;

  public FileQuery() {
  }

  public FileQuery(Long allianceId, Long affairId, String query) {
    this.affairId = affairId;
    this.allianceId = allianceId;
    setQuery(query);
  }

  public Long getAffairId() {
    return affairId;
  }

  public void setAffairId(Long affairId) {
    this.affairId = affairId;
  }

  public Long getAllianceId() {
    return allianceId;
  }

  public void setAllianceId(Long allianceId) {
    this.allianceId = allianceId;
  }

  @Override
  public String toString() {
    return "FileQuery{" +
        "allianceId=" + allianceId +
        ", affairId=" + affairId +
        ", stringQuery=" + super.toString() +
        '}';
  }
}
