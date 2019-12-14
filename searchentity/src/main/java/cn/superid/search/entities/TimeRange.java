package cn.superid.search.entities;

import lombok.Data;

/**
 * @author zzt
 */
@Data
public class TimeRange {

  private String timeFieldName;
  private long start;
  private long end;

  public TimeRange setTimeFieldName(String timeFieldName) {
    this.timeFieldName = timeFieldName;
    return this;
  }
}
