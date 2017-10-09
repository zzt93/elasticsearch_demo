package cn.superid.search.impl.entities.user.warehouse;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import cn.superid.search.entities.user.warehouse.MaterialQuery;
import cn.superid.search.impl.save.rolling.Suffix;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.util.Assert;

/**
 * @author zzt
 */
public class MaterialRepoImpl implements MaterialCustom {

  private final ElasticsearchTemplate template;

  @Autowired
  public MaterialRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }

  @Override
  public Page<MaterialPO> findByAllInfo(MaterialQuery info, Pageable pageable) {
    Assert.notNull(info.getAllianceId(), "[Lacking allianceId]");
    Assert.notNull(info.getQuery(), "[Lacking query string]");

    BoolQueryBuilder bool = boolQuery()
        .should(matchQuery("name", info.getQuery()))
        .should(nestedQuery("tags", matchQuery("tags.des", info.getQuery()), ScoreMode.Avg));
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
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexName(MaterialPO.class, info.getAllianceId()))
        .withQuery(bool).build();
    return template.queryForPage(searchQuery, MaterialPO.class);
  }
}
