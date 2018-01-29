package cn.superid.search.impl.entities.user.warehouse;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import cn.superid.search.entities.ScrollQuery;
import cn.superid.search.entities.user.warehouse.MaterialQuery;
import cn.superid.search.impl.save.rolling.Suffix;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author zzt
 */
@Component
public class MaterialRepoImpl implements MaterialCustom {

  private final ElasticsearchTemplate template;

  @Autowired
  public MaterialRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }

  @Override
  public Page<MaterialPO> findByAllInfo(MaterialQuery info, Pageable pageable,
      SearchResultMapper mapper) {
    Assert.notNull(info.getAllianceId(), "[Lacking allianceId]");
    Assert.notNull(info.getQuery(), "[Lacking query string]");

    BoolQueryBuilder bool = boolQuery()
        .should(matchQuery("name", info.getQuery()));
    if (info.getAffairId() != null) {
      bool.filter(termQuery("affairId", info.getAffairId()));
    }
    if (info.getMaterialType() != null) {
      bool.filter(termQuery("type", info.getMaterialType()));
    }
    if (info.getPublicType() != null) {
      bool.filter(termQuery("publicType", info.getPublicType()));
    }
    if (info.getWarehouseId() != null) {
      bool.filter(termQuery("warehouseId", info.getWarehouseId()));
    }
    if (info.getTags() != null) {
      bool.filter(termQuery("tags", info.getQuery()));
    }
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(
            Suffix.indexName(MaterialPO.class, info.getAllianceId() / MaterialPO.CLUSTER_SIZE))
        .withQuery(bool)
        .withPageable(pageable).build();
    return template.startScroll(ScrollQuery.SCROLL_TIME_IN_MILLIS, searchQuery, MaterialPO.class,
        mapper);
  }

  @Override
  public Page<MaterialPO> findByAllInfo(ScrollQuery query, SearchResultMapper mapper) {
    return template
        .continueScroll(query.getScrollId(), ScrollQuery.SCROLL_TIME_IN_MILLIS, MaterialPO.class,
            mapper);
  }
}
