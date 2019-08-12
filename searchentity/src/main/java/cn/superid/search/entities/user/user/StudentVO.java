package cn.superid.search.entities.user.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class StudentVO extends PersonalAffairVO {

  private String[] sids;

  public StudentVO(long affairId, long userId, String[] sids) {
    super(affairId, userId);
    this.sids = sids;
  }
}
