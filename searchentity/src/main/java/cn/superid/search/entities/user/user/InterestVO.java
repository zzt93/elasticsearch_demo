package cn.superid.search.entities.user.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterestVO extends PersonalAffairVO {

  private String[] tags;

  public InterestVO(long affairId, long userId, String[] tags) {
    super(affairId, userId);
    this.tags = tags;
  }
}
