package cn.superid.search.impl.entities.time.audit;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.time.audit.AuditQuery;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author zzt
 */
public class AuditRepoImpl implements AuditCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<AuditPO> findByQuery(AuditQuery info) {
    PageRequest pageRequest = info.getPageRequest();
    Preconditions.checkArgument(pageRequest != null);
    pageRequest = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(),
        Sort.by(Order.desc("_score"), Order.desc("sendTime")));
    Preconditions.checkArgument(info.getQuery() != null);

    BoolQueryBuilder bool = boolQuery()
        .must(
            boolQuery()
                .should(wildcardQuery("content", QueryHelper.wildcard(info.getQuery())))
        )
        .filter(termsQuery("receiverRoleId", info.getReceiver()))
        .filter(termsQuery("senderRoleId", info.getReceiver())
        );
    if (info.getStates() != null) {
      bool.filter(termsQuery("handleState", info.getStates()));
    }

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(
            Suffix.indexNamePattern(AuditPO.class))
        .withQuery(bool)
        .withPageable(pageRequest).build();
    return template.queryForPage(searchQuery, AuditPO.class);
  }
}
