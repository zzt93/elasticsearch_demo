package cn.superid.search.impl.query;

import java.util.List;
import java.util.function.BiConsumer;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

/**
 * @author zzt
 */
public class HighlightMapper<R> extends DefaultResultMapper {

  private BiConsumer<SearchHit, R> function;

  public HighlightMapper(BiConsumer<SearchHit, R> s) {
    function = s;
  }

  @Override
  public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
      Pageable pageable) {
    AggregatedPage<T> res = super.mapResults(response, clazz, pageable);
    List<T> chunk = res.getContent();
    SearchHits hits = response.getHits();
    for (int i = 0; i < hits.getTotalHits(); i++) {
      SearchHit at = hits.getAt(i);
      T t = chunk.get(i);
      function.accept(at, (R) t);
    }
    if (chunk.size() > 0) {
      return new AggregatedPageImpl<>(chunk);
    }
    return null;
  }
}
