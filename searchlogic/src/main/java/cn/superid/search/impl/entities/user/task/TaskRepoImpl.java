package cn.superid.search.impl.entities.user.task;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.user.task.TaskQuery;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.Arrays;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
public class TaskRepoImpl implements TaskCustom {

  private final ElasticsearchTemplate template;

  @Autowired
  public TaskRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }

  @Override
  public Page<TaskPO> findByAll(TaskQuery taskQuery) {
    Preconditions.checkNotNull(taskQuery.getQuery(), "No query string");
    Preconditions.checkNotNull(taskQuery.getUserId(), "No user id provided");

    BoolQueryBuilder bool = boolQuery()
        .filter(termQuery("users", taskQuery.getUserId()))
        .filter(termQuery("type", 0));

    if (!StringUtils.isEmpty(taskQuery.getQuery())) {
      bool.must(wildcardQuery("title", QueryHelper.wildcard(taskQuery.getQuery())));
    }
    if (taskQuery.getState() != null) {
      bool.filter(termQuery("state", taskQuery.getState()));
    } else {
      bool.filter(termsQuery("state", Arrays.asList(0, 1, 2)));
    }
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(TaskPO.class))
        .withQuery(bool)
        .withPageable(taskQuery.getPageRequest())
        .withSourceFilter(new FetchSourceFilter(new String[]{"_id"}, null))
        .build();
    return template.queryForPage(searchQuery, TaskPO.class);
  }
}
