package cn.superid.search.impl.entities.announcement;

import static org.junit.Assert.assertEquals;

import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.junit.Test;

/**
 * @author zzt
 */
public class DateTest {

  private static final Timestamp modifyTime;
  private static final Long affairId = 9038L;

  static {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm:ss.SSS");
    Date parsedDate = null;
    try {
      parsedDate = dateFormat.parse("2016.10.11 10:10:10.100");
    } catch (ParseException e) {
      e.printStackTrace();
    }
    modifyTime = new Timestamp(parsedDate.getTime());
  }

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

  @Test
  public void parseTime() throws Exception {
    String source = "{\"affairId\":11097,\"modifyTime\":\"2017-11-14 14:43:31.0\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[]}";
    String source4 = "{\"affairId\":11097,\"modifyTime\":\"2009-10-04 22:23:00.123\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[]}";
    ObjectMapper mapper = new ObjectMapper();
    AnnouncementPO po1 = mapper.readValue(source, AnnouncementPO.class);
    assertEquals(po1.getModifyTime().getTime(), 1510670611000L);
    AnnouncementPO po2 = mapper.readValue(source4, AnnouncementPO.class);
    assertEquals(po2.getModifyTime().getTime(), 1254694980123L);
  }

  @Test
  public void toJson() throws Exception {
    String role2 = "role2";
    String t1 = "t1";
    String t2 = "t2";
    AnnouncementPO a1 = new AnnouncementPO("11", "announcement1", "content",
        new String[]{t1, t2},
        new long[]{1L}, role2, affairId, modifyTime);

    ObjectMapper mapper = new ObjectMapper();
    String json = mapper.writeValueAsString(a1);
    assertEquals(json,
        "{\"title\":\"announcement1\",\"content\":\"content\",\"tags\":[\"t1\",\"t2\"],\"affairId\":9038,\"modifyTime\":\"2016-10-11 02:10:10.100\",\"roles\":[1],\"plateType\":null,\"type\":null}");
  }
}
