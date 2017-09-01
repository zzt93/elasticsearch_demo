package cn.superid.search.impl.query;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

/**
 * The mapper used to add the highlight information
 *
 * @author zzt
 */
public class HighlightMapper<R> extends DefaultResultMapper {

  private BiConsumer<Map<String, HighlightField>, R> function;

  public HighlightMapper(BiConsumer<Map<String, HighlightField>, R> s) {
    function = s;
  }

  @Override
  public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
      Pageable pageable) {
    AggregatedPage<T> res = super.mapResults(response, clazz, pageable);
    List<T> chunk = res.getContent();
    SearchHits hits = response.getHits();
    for (int i = 0; i < hits.hits().length; i++) {
      SearchHit at = hits.getAt(i);
      T t = chunk.get(i);
      function.accept(at.getHighlightFields(), (R) t);
    }
    if (chunk.size() > 0) {
      return new AggregatedPageImpl<>(chunk);
    }
    return null;
  }
}
