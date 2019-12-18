package cn.superid.search.entities.time.announcement;

import cn.superid.search.entities.MyQuery;
import cn.superid.search.entities.TimeRange;
import cn.superid.search.entities.time.announcement.AnnouncementQuery.AnnType;
import java.util.Collections;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class MyAnnQuery extends MyQuery {

  private TimeRange create;
  private TimeRange modify;
  private List<Byte> states;
  private List<Integer> roleTypes;
  private List<AnnType> types = Collections.emptyList();

}
