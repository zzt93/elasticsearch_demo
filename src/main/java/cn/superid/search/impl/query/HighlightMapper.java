package cn.superid.search.impl.query;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author zzt
 */
public class HighlightMapper<R> implements SearchResultMapper {

    private Function<SearchHit, R> function;

    public HighlightMapper(Function<SearchHit, R> s) {
        function = s;
    }

    @Override
    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
        List<R> chunk = new ArrayList<>();
        for (SearchHit searchHit : response.getHits()) {
            if (response.getHits().getHits().length <= 0) {
                return null;
            }
            R entity = function.apply(searchHit);
            chunk.add(entity);
        }
        if (chunk.size() > 0) {
            return new AggregatedPageImpl<>((List<T>) chunk);
        }
        return null;
    }
}
