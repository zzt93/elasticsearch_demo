package cn.superid.search.entities.user.affair;

import cn.superid.search.entities.PagedQuery;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 * @see PagedQuery
 */
@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
@NoArgsConstructor
public class AffairQuery extends PagedQuery {
  private String[] tags;
  private long allianceId;
  private List<Byte> molds;
  private List<Byte> excludeMolds;

  public AffairQuery(String query, PageRequest pageRequest) {
    setQuery(query);
    setPageRequest(pageRequest);
  }

  public AffairQuery(String query, PageRequest pageRequest, String[] tags) {
    setQuery(query);
    setPageRequest(pageRequest);
    this.tags = tags;
  }

  public AffairQuery setAllianceId(long allianceId) {
    this.allianceId = allianceId;
    return this;
  }

  public boolean isMobile(Pattern mobile) {
    return getPageRequest().getPageNumber() == 0 && mobile.matcher(getQuery()).find();
  }
}
