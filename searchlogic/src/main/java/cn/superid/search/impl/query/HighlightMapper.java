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
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;

/**
 * The mapper used to add the highlight information
 *
 * @author zzt
 */
public class HighlightMapper<R> extends DefaultResultMapper {

  private BiConsumer<Map<String, HighlightField>, R> function;


  public HighlightMapper(ElasticsearchConverter elasticsearchConverter,
      BiConsumer<Map<String, HighlightField>, R> s) {
    super(elasticsearchConverter.getMappingContext());
    function = s;
  }

  @SuppressWarnings({"Because the DefaultResultMapper's interface"})
  @Override
  public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
      Pageable pageable) {
    AggregatedPage<T> res = super.mapResults(response, clazz, pageable);
    List<T> chunk = res.getContent();
    SearchHits hits = response.getHits();
    for (int i = 0; i < hits.getHits().length; i++) {
      SearchHit at = hits.getAt(i);
      T t = chunk.get(i);
      function.accept(at.getHighlightFields(), (R) t);
    }
    if (chunk.size() > 0) {
      return res;
    }
    return null;
  }
}
