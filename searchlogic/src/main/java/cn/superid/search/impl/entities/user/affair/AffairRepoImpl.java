package cn.superid.search.impl.entities.user.affair;

import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.impl.save.rolling.Suffix;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * @author zzt
 */
public class AffairRepoImpl implements AffairCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<AffairPO> findAny(String info, Pageable pageable) {
    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("state", 0))
        .must(
            boolQuery()
                .should(termQuery("superId", info))
                .should(wildcardQuery("name", wildcard(info)))
                .should(termQuery("tags", info))
        );
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(AffairPO.class))
        .withQuery(bool)
        .withPageable(pageable)
        .build();
    return template.queryForPage(searchQuery, AffairPO.class);
  }

  @Override
  public List<AffairPO> findAlliance(String info, Pageable pageable) {
    BoolQueryBuilder bool = boolQuery()
        .must(termQuery("state", 0))
        .must(termQuery("parentId", 0))
        .must(
            boolQuery()
                .should(wildcardQuery("name", wildcard(info)))
        );
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(AffairPO.class))
        .withQuery(bool)
        .withPageable(pageable)
        .build();
    return template.queryForPage(searchQuery, AffairPO.class).getContent();
  }
}
