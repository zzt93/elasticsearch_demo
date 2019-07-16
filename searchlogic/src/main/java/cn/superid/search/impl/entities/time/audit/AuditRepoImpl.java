package cn.superid.search.impl.entities.time.audit;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.time.audit.AuditQuery;
import cn.superid.search.entities.time.audit.AuditUserQuery;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    Preconditions.checkArgument(info.getQuery() != null);

    BoolQueryBuilder bool = boolQuery()
        .must(wildcardQuery("content", QueryHelper.wildcard(info.getQuery())));
    if (info.getSender() != null && info.getReceiver() != null) {
      bool.must(
          boolQuery()
              .should(termQuery("senderRoleId", info.getSender()))
              .should(termQuery("receiverRoleId", info.getReceiver()))
      );
    } else if (info.getSender() != null) {
      bool.must(termQuery("senderRoleId", info.getSender()));
    } else if (info.getReceiver() != null) {
      bool.must(termQuery("receiverRoleId", info.getReceiver()));
    }
    if (info.getStates() != null) {
      bool.filter(termsQuery("handleState", info.getStates()));
    }
    if (info.getFrom() != null) {
      bool.filter(rangeQuery("sendTime")
          .gt(info.getFrom())
          .lt(info.getTo()));
    }

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(
            Suffix.indexNamePattern(AuditPO.class))
        .withQuery(bool)
        .withPageable(pageRequest).build();
    return template.queryForPage(searchQuery, AuditPO.class);
  }

  @Override
  public Page<AuditPO> findByUserQuery(AuditUserQuery info) {
    PageRequest pageRequest = info.getPageRequest();
    Preconditions.checkArgument(pageRequest != null);
    Preconditions.checkArgument(info.getQuery() != null);
    Preconditions.checkArgument(info.getRoles() != null);

    BoolQueryBuilder bool = boolQuery()
        .must(wildcardQuery("content", QueryHelper.wildcard(info.getQuery())));
    bool.must(
        boolQuery()
            .should(termQuery("senderRoleId", info.getRoles()))
            .should(termQuery("receiverRoleId", info.getRoles()))
    );

    bool.filter(termsQuery("handleState", 0));


    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(
            Suffix.indexNamePattern(AuditPO.class))
        .withQuery(bool)
        .withPageable(pageRequest).build();
    return template.queryForPage(searchQuery, AuditPO.class);
  }

}
