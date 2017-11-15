package cn.superid.search.impl.entities.announcement;

import cn.superid.search.impl.entities.TagPO;
import cn.superid.search.impl.entities.time.announcement.AnnouncementPO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.sql.Timestamp;
import java.text.ParseException;
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

  @Test
  public void parseTime() throws Exception {
    String source = "{\"affairId\":11097,\"affairName\":\"测试的盟\",\"creatorRoleId\":6809,\"modifyTime\":\"2017-11-14 14:43:31.0\",\"creatorRole\":\"负责人\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[{\"des\":\"[]\"}]}";
    String source4 = "{\"affairId\":11097,\"affairName\":\"测试的盟\",\"creatorRoleId\":6809,\"modifyTime\":\"2009-10-04 22:23:00.123\",\"creatorRole\":\"负责人\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[{\"des\":\"[]\"}]}";
//    String source5 = "{\"affairId\":11097,\"affairName\":\"测试的盟\",\"creatorRoleId\":6809,\"modifyTime\":\"2009-10-04 22:23:00.123456\",\"creatorRole\":\"负责人\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[{\"des\":\"[]\"}]}";
//    String source2 = "{\"affairId\":11097,\"affairName\":\"测试的盟\",\"creatorRoleId\":6809,\"modifyTime\":\"2017-11-06T06:38:45.000Z\",\"creatorRole\":\"负责人\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[{\"des\":\"[]\"}]}";
//    String source3 = "{\"affairId\":11097,\"affairName\":\"测试的盟\",\"creatorRoleId\":6809,\"modifyTime\":\"1476151810100\",\"creatorRole\":\"负责人\",\"title\":\"en\",\"content\":\"eyJibG9ja3MiOlt7ImRhdGEiOltdLCJkZXB0aCI6MCwiZW50aXR5UmFuZ2VzIjpbXSwia2V5IjoiNXByZmEiLCJ0ZXh0IjoiZW4xMjMiLCJ0eXBlIjoidW5zdHlsZWQifV0sImVudGl0eU1hcCI6eyIwIjp7ImRhdGEiOnsic3JjIjoiaHR0cDovL3NpbXVjeS5vc3MtY24tc2hhbmdoYWkuYWxpeXVuY3MuY29tL2FmZmFpci8xMTA5Ny9hbm5vdW5jZW1lbnQv5bCB6Z2i5Zu+LmpwZyJ9LCJtdXRhYmlsaXR5IjoiSU1NVVRBQkxFIiwidHlwZSI6Ik1FRElBIn19fQ==\",\"tags\":[{\"des\":\"[]\"}]}";
    ObjectMapper mapper = new ObjectMapper();
    mapper.readValue(source, AnnouncementPO.class);
    AnnouncementPO announcementPO = mapper.readValue(source4, AnnouncementPO.class);
    System.out.println(announcementPO);
//    mapper.readValue(source2, AnnouncementPO.class);
//    mapper.readValue(source3, AnnouncementPO.class);
  }

  private static final Timestamp modifyTime;
  private static final Long affairId = 9038L;
  private static TagPO t1 = new TagPO("t1");
  private static TagPO t2 = new TagPO("t2");

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
  public void toJson() throws Exception {
    String role1 = "role1";
    String role2 = "role2";
    AnnouncementPO a1 = new AnnouncementPO("11", "announcement1", "content",
        Lists.newArrayList(t1, t2),
        role1, role2, affairId, modifyTime);

    ObjectMapper mapper = new ObjectMapper();
    System.out.println(mapper.writeValueAsString(a1));
  }
}
