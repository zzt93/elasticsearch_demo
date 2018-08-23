package cn.superid.search.impl.entities.user.target;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.user.target.TargetQuery;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author zzt
 */
public class TargetCustomImpl implements TargetCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public List<TargetPO> findByNameAndAffairIdIn(TargetQuery query) {
    Preconditions.checkArgument(!query.getQuery().isEmpty());
    List<Long> affairs = query.getAffairs();
    String suffix;
    if (affairs.size() == 1) {
      suffix = Suffix.indexName(TargetPO.class, affairs.get(0) / TargetPO.CLUSTER_SIZE);
    } else {
      suffix = Suffix.indexNamePattern(TargetPO.class);
    }
    BoolQueryBuilder bool = boolQuery()
        .must(wildcardQuery("name", QueryHelper.wildcard(query.getQuery())))
        .filter(termsQuery("affairId", affairs));
    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(suffix)
        .withQuery(bool).build();
    return template.queryForList(searchQuery, TargetPO.class);
  }
}
