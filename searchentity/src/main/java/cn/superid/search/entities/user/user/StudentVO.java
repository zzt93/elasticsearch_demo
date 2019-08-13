package cn.superid.search.entities.user.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
public class StudentVO extends PersonalAffairVO {

  private String[] sids;

  public StudentVO(long userId, long affairId, String[] sids) {
    super(affairId, userId);
    this.sids = sids;
  }
}
