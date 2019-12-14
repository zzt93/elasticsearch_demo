package cn.superid.search.impl.util;

import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

import cn.superid.search.entities.TimeRange;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.elasticsearch.index.query.RangeQueryBuilder;

/**
 * @author zzt
 */
public class TimeUtil {

  public static RangeQueryBuilder timeRange(TimeRange range, SimpleDateFormat dateFormat) {
    return rangeQuery(range.getTimeFieldName())
        .gt(dateFormat.format(new Date(range.getStart())))
        .lt(dateFormat.format(new Date(range.getEnd())));
  }


}
