package cn.superid.search.impl.entities.time.chat;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.elasticsearch.search.aggregations.AggregationBuilders.terms;

import cn.superid.search.entities.time.chat.ChatIdsQuery;
import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.impl.DefaultFetchSource;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.Map;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms.Bucket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

/**
 * @author zzt
 */
@Service
public class MessagesRepoImpl implements MessagesCustom {

  private final ElasticsearchTemplate template;

  @Autowired
  public MessagesRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }

  @Override
  public Page<MessagesPO> findByMessage(ChatQuery info, Pageable pageable) {
    Preconditions.checkArgument(pageable != null);
    Preconditions.checkArgument((info.getSubType() == null) || (info.getSubTypes() == null), "Invalid subType or subTypes");

    BoolQueryBuilder bool = boolQuery()
        .filter(termQuery("chatId", info.getChatId()))
        .mustNot(termQuery("state", 1));
    if (info.getQuery() != null) {
      bool.must(wildcardQuery("content", QueryHelper.wildcard(info.getQuery())));
    }
    if (info.getStartTime() != null && info.getEndTime() != null) {
      bool.filter(rangeQuery("time").gte(info.getStartTime()).lte(info.getEndTime()));
    }
    if (info.getSubType() != null) {
      bool.filter(termQuery("sub", info.getSubType()));
    }
    if (info.getSubTypes() != null) {
      bool.filter(termsQuery("sub", info.getSubTypes()));
    }

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexName(MessagesPO.class, ""))
        .withQuery(bool)
        .withSourceFilter(DefaultFetchSource.defaultId())
        .withPageable(pageable).build();
    return template.queryForPage(searchQuery, MessagesPO.class);
  }

  @Override
  public Map<String, Long> countMessage(ChatIdsQuery info) {

    BoolQueryBuilder bool = boolQuery()
        .filter(termsQuery("chatId", info.getChatIds()))
        .mustNot(termQuery("state", 1));
    if (info.getQuery() != null) {
      bool.must(wildcardQuery("content", QueryHelper.wildcard(info.getQuery())));
    }
    if (info.getStartTime() != null && info.getEndTime() != null) {
      bool.filter(rangeQuery("time").gte(info.getStartTime()).lte(info.getEndTime()));
    }
    if (info.getSubType() != null) {
      bool.filter(termQuery("sub", info.getSubType()));
    }

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexName(MessagesPO.class, ""))
        .withTypes("messages")
//        .withPageable(info.getPageRequest())
        .addAggregation(terms("ids").field("chatId"))
        .withQuery(bool).build();

    Aggregations aggregations = template.query(searchQuery, SearchResponse::getAggregations);
    StringTerms ids = (StringTerms) aggregations.asMap().get("ids");
    Map<String, Long> res = new HashMap<>();
    for (Bucket bucket : ids.getBuckets()) {
      res.put(bucket.getKeyAsString(), bucket.getDocCount());
    }
    return res;
  }

}
