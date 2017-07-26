package cn.superid.search.impl.query.time.announcement;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

/**
 * @author zzt
 */
public class DateTest {

  @Test
  public void format() throws Exception {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Date date = new Date(timestamp.getTime());

    // S is the millisecond
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    System.out.println(simpleDateFormat.format(timestamp));
    System.out.println(simpleDateFormat.format(date));
  }
}
