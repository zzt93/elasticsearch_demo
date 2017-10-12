package cn.superid.search.entities;

/**
 * @author zzt
 */
public class ScrollQuery {

  public static final long SCROLL_TIME_IN_MILLIS = 5 * 60 * 1000L;
  private long scrollTimeInMill;
  private String scrollId;

  public ScrollQuery(long scrollTimeInMill, String scrollId) {
    this.scrollTimeInMill = scrollTimeInMill;
    this.scrollId = scrollId;
  }

  public ScrollQuery(String scrollId) {
    this.scrollId = scrollId;
  }

  public long getScrollTimeInMill() {
    return scrollTimeInMill;
  }

  public void setScrollTimeInMill(long scrollTimeInMill) {
    this.scrollTimeInMill = scrollTimeInMill;
  }

  public String getScrollId() {
    return scrollId;
  }

}
