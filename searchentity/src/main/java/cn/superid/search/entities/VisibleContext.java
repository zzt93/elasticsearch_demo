package cn.superid.search.entities;

import java.util.List;
import lombok.Data;

/**
 * @author zzt
 */
@Data
public class VisibleContext {

  private List<Long> affairs;
  private List<Long> alliances;

  private UserInfo selfInfo;

  @Data
  public static class UserInfo {
    private List<Long> roles;
    private Long role;
    private Long userId;

    public UserInfo() {
    }

    public UserInfo(List<Long> roles) {
      this.roles = roles;
    }

    public UserInfo(Long userId) {
      this.userId = userId;
    }

    public UserInfo(List<Long> roles, Long userId) {
      this.roles = roles;
      this.userId = userId;
    }
  }

}
