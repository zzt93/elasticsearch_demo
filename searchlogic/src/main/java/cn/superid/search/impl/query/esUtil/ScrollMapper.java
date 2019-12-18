package cn.superid.search.impl.query.esUtil;

import org.elasticsearch.action.search.SearchResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * @author zzt
 */
public class ScrollMapper extends DefaultResultMapper {

  private String scrollId;

  public ScrollMapper(ElasticsearchConverter elasticsearchConverter) {
    super(elasticsearchConverter.getMappingContext());
  }

  @Override
  public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
      Pageable pageable) {
    // TODO 17/10/17 opt: only return id, not deserialize other field
    AggregatedPage<T> res = super.mapResults(response, clazz, pageable);
    scrollId = response.getScrollId();
    return res;
  }

  public String getScrollId() {
    return scrollId;
  }
}
