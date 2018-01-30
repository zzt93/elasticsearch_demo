package cn.superid.search.impl.entities.time;

import cn.superid.search.entities.RollingIndex;

/**
 * @author zzt
 */
public interface TimeBasedIndex extends RollingIndex {


  static String timeFormat() {
    return "yyyy-MM-dd";
  }

  int timeFormatLen();

}
