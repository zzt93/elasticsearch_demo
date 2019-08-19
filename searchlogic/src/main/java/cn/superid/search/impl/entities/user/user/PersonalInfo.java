package cn.superid.search.impl.entities.user.user;

import lombok.Data;

/**
 * @author zzt
 */
@Data
public class PersonalInfo {

  private String id;
  private long affairId;
  private Short type;
  private String content;
  private String description;

}
