package cn.superid.search.impl.query;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;

/**
 * @author zzt
 */
public class ScrollMapper extends DefaultResultMapper {

  private String scrollId;

  @Override
  public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
      Pageable pageable) {
    AggregatedPage<T> res = super.mapResults(response, clazz, pageable);
    scrollId = response.getScrollId();
    return res;
  }

  public String getScrollId() {
    return scrollId;
  }
}
