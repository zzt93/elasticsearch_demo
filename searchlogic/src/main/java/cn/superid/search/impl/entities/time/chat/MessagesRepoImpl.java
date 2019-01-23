package cn.superid.search.impl.entities.time.chat;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;
import static org.springframework.util.CollectionUtils.isEmpty;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.time.chat.ChatIdsQuery;
import cn.superid.search.entities.time.chat.ChatQuery;
import cn.superid.search.entities.time.chat.MessagesVO;
import cn.superid.search.impl.DefaultFetchSource;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elasticsearch.action.ActionFuture;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.MultiSearchResponse.Item;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.search.aggregations.AbstractAggregationBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.facet.FacetRequest;
import org.springframework.data.elasticsearch.core.query.IndexBoost;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.ScriptField;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.SourceFilter;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author zzt
 */
@Service
public class MessagesRepoImpl implements MessagesCustom {

  private final ElasticsearchTemplate template;

  private DefaultResultMapper resultMapper;

  @Autowired
  public MessagesRepoImpl(ElasticsearchTemplate template,
      ElasticsearchConverter elasticsearchConverter) {
    this.template = template;
    resultMapper = new DefaultResultMapper(elasticsearchConverter.getMappingContext());
  }

  private static String[] toArray(List<String> values) {
    String[] valuesAsArray = new String[values.size()];
    return values.toArray(valuesAsArray);
  }

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
        .withSourceFilter(DefaultFetchSource.defaultId())
        .withPageable(pageable).build();
    return template.queryForPage(searchQuery, MessagesPO.class);
  }

  @Override
  public Map<String, PageVO<MessagesVO>> findByMessage(ChatIdsQuery info) {
    Client client = template.getClient();
    MultiSearchRequest request = new MultiSearchRequest();
    List<String> chatIds = info.getChatIds();
    for (String id : chatIds) {
      BoolQueryBuilder bool = boolQuery()
          .filter(termQuery("chatId", id))
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
          .withSourceFilter(DefaultFetchSource.defaultId())
          .withPageable(info.getPageRequest())
          .withQuery(bool).build();
      request.add(prepareSearch(client, searchQuery));
    }
    ActionFuture<MultiSearchResponse> future = client
        .multiSearch(request);
    MultiSearchResponse response = future.actionGet();
    Item[] items = response.getResponses();
    Map<String, PageVO<MessagesVO>> res = new HashMap<>();
    for (int i = 0; i < chatIds.size(); i++) {
      AggregatedPage<MessagesPO> ts = resultMapper.mapResults(items[i].getResponse(), MessagesPO.class, info.getPageRequest());
      res.put(chatIds.get(i), new PageVO<>(ts, VoAndPoConversion::toVO));
    }
    return res;
  }

  private SearchRequestBuilder prepareSearch(Client client, SearchQuery searchQuery) {
    Assert.notNull(searchQuery.getIndices(), "No index defined for Query");
    Assert.notNull(searchQuery.getTypes(), "No type defined for Query");

    int startRecord = 0;
    SearchRequestBuilder searchRequest = client.prepareSearch(toArray(searchQuery.getIndices()))
        .setSearchType(searchQuery.getSearchType()).setTypes(toArray(searchQuery.getTypes()));

    if (searchQuery.getSourceFilter() != null) {
      SourceFilter sourceFilter = searchQuery.getSourceFilter();
      searchRequest.setFetchSource(sourceFilter.getIncludes(), sourceFilter.getExcludes());
    }

    if (searchQuery.getPageable().isPaged()) {
      startRecord = searchQuery.getPageable().getPageNumber() * searchQuery.getPageable().getPageSize();
      searchRequest.setSize(searchQuery.getPageable().getPageSize());
    }
    searchRequest.setFrom(startRecord);

    if (!searchQuery.getFields().isEmpty()) {
      searchRequest.setFetchSource(toArray(searchQuery.getFields()), null);
    }

    if (searchQuery.getSort() != null) {
      for (Sort.Order order : searchQuery.getSort()) {
        searchRequest.addSort(order.getProperty(),
            order.getDirection() == Sort.Direction.DESC ? SortOrder.DESC : SortOrder.ASC);
      }
    }

    if (searchQuery.getMinScore() > 0) {
      searchRequest.setMinScore(searchQuery.getMinScore());
    }

    if (searchQuery.getFilter() != null) {
      searchRequest.setPostFilter(searchQuery.getFilter());
    }

    if (!isEmpty(searchQuery.getElasticsearchSorts())) {
      for (SortBuilder sort : searchQuery.getElasticsearchSorts()) {
        searchRequest.addSort(sort);
      }
    }

    if (!searchQuery.getScriptFields().isEmpty()) {
      //_source should be return all the time
      //searchRequest.addStoredField("_source");
      for (ScriptField scriptedField : searchQuery.getScriptFields()) {
        searchRequest.addScriptField(scriptedField.fieldName(), scriptedField.script());
      }
    }

    if (searchQuery.getHighlightFields() != null) {
      for (HighlightBuilder.Field highlightField : searchQuery.getHighlightFields()) {
        searchRequest.highlighter(new HighlightBuilder().field(highlightField));
      }
    }

    if (!isEmpty(searchQuery.getIndicesBoost())) {
      for (IndexBoost indexBoost : searchQuery.getIndicesBoost()) {
        searchRequest.addIndexBoost(indexBoost.getIndexName(), indexBoost.getBoost());
      }
    }

    if (!isEmpty(searchQuery.getAggregations())) {
      for (AbstractAggregationBuilder aggregationBuilder : searchQuery.getAggregations()) {
        searchRequest.addAggregation(aggregationBuilder);
      }
    }

    if (!isEmpty(searchQuery.getFacets())) {
      for (FacetRequest aggregatedFacet : searchQuery.getFacets()) {
        searchRequest.addAggregation(aggregatedFacet.getFacet());
      }
    }

    return searchRequest.setQuery(searchQuery.getQuery());
  }

}
