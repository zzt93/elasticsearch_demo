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
public class InterestVO extends PersonalAffairVO {

  private String[] tags;

  public InterestVO(long userId, long affairId, String[] tags) {
    super(affairId, userId);
    this.tags = tags;
  }
}
