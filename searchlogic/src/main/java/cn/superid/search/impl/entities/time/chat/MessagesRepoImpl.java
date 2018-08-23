package cn.superid.search.impl.entities.time.chat;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author zzt
 */
public class MessagesRepoImpl implements MessagesCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<MessagesPO> findByMessage(ChatQuery info, Pageable pageable) {
    Preconditions.checkArgument(pageable != null);

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

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexName(MessagesPO.class, ""))
        .withQuery(bool)
        .withPageable(pageable).build();
    return template.queryForPage(searchQuery, MessagesPO.class);
  }
}
