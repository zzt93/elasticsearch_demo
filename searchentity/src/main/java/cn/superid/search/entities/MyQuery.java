package cn.superid.search.entities;

import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author zzt
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyQuery extends PagedQuery {

  private List<Long> roles;

}
