package cn.superid.search.impl.entities.time.chat;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.impl.query.HighlightMapper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder.Field;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author zzt
 */
public class MessagesRepoImpl implements MessagesCustom {

  @Autowired
  private ElasticsearchTemplate template;
  @Autowired
  private ElasticsearchConverter elasticsearchConverter;

  @Override
  public Page<MessagesPO> findByMessage(ChatQuery info, Pageable pageable) {
    Preconditions.checkArgument(pageable != null);

    BoolQueryBuilder bool = boolQuery()
        .should(matchQuery("chatId", info.getChatId()));
    if (info.getQuery() != null) {
      bool.should(wildcardQuery("content", "*" + info.getQuery() + "*"));
    }
    if (info.getStartTime() != 0 && info.getEndTime() != 0) {
      bool.filter(rangeQuery("time").gte(info.getStartTime()).lte(info.getEndTime()));
    }
    if (info.getSubType() != 0) {
      bool.filter(termQuery("sub", info.getSubType()));
    }

    NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(
            Suffix.timeBasedPattern(MessagesPO.class, info.getStartTime(), info.getEndTime()))
        .withQuery(bool).withHighlightFields(new Field("content"))
        .withPageable(pageable).build();
    return template.queryForPage(searchQuery, MessagesPO.class,
        new HighlightMapper<MessagesPO>(elasticsearchConverter, (highlightFields, message) -> {
          HighlightField content = highlightFields.get("content");
          if (content != null) {
            message.setContent(content.fragments()[0].toString());
          }
        }));
  }
}
