package cn.superid.search.entities.user.user;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class InterestVO extends PersonalAffairVO {

  private List<String> tags;

}
