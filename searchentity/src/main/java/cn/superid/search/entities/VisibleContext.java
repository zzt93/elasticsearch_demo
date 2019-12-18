package cn.superid.search.entities;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zzt
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisibleContext {

  private List<Long> affairs;
  private List<Long> alliances;

  private UserInfo selfInfo;

  @Data
  @Builder
  @NoArgsConstructor
  @AllArgsConstructor
  public static class UserInfo {
    private List<Long> roles;
    private Long role;
    private Long userId;
  }

}
