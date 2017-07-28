package cn.superid.search.entities.time;

import cn.superid.search.entities.RollingIndex;
import java.sql.Timestamp;

/**
 * @author zzt
 */
public interface TimeBasedIndex extends RollingIndex {


  Timestamp getCreateTime();

  void setCreateTime(Timestamp createTime);

}
