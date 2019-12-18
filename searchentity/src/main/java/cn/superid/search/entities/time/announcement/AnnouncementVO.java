package cn.superid.search.entities.time.announcement;

import java.util.Map;
import lombok.Data;

/**
 * Created by zzt on 17/5/27.
 */
@Data
public class AnnouncementVO {

  private String id;
  private String title;
  private String thumbContent;
  private Map[] roles;

  public AnnouncementVO() {
  }

}
