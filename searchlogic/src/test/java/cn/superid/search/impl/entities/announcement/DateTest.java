package cn.superid.search.impl.entities.announcement;

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

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");

    System.out.println(simpleDateFormat.format(timestamp));
    System.out.println(simpleDateFormat.format(date));
  }

  @Test
  public void format2() throws Exception {
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Date date = new Date(timestamp.getTime());

    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM");

    System.out.println(simpleDateFormat.format(timestamp));
    System.out.println(simpleDateFormat.format(date));
  }
}
