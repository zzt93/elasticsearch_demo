package cn.superid.search.impl.entities.user.task;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.user.task.TaskQuery;
import cn.superid.search.impl.DefaultFetchSource;
import cn.superid.search.impl.query.QueryHelper;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
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

    BoolQueryBuilder bool = boolQuery();

    if ((taskQuery.getNormalTask() && taskQuery.getWorkflowTask())
        || (taskQuery.getNormalTask() == null && taskQuery.getWorkflowTask() == null)){
      //do nothing
    }else if (taskQuery.getNormalTask()){
      bool.filter(termQuery("workFlowId", 0));
    }else if (taskQuery.getWorkflowTask()){
      bool.mustNot(termQuery("workFlowId", 0));
    }else{
      return null;
    }

    if (!StringUtils.isEmpty(taskQuery.getQuery())) {
      bool.must(
          boolQuery()
              .should(wildcardQuery("title", QueryHelper.wildcard(taskQuery.getQuery())))
              .should(wildcardQuery("fromName", QueryHelper.wildcard(taskQuery.getQuery())))
      );
    }
    if (taskQuery.getRoles() != null) {
      bool.filter(termsQuery("roles", taskQuery.getRoles()));
    }
    if (taskQuery.getStates() != null) {
      bool.filter(termsQuery("state", taskQuery.getStates()));
    }
    if (taskQuery.getTypes() != null) {
      bool.filter(termsQuery("type", taskQuery.getTypes()));
    }
    if (taskQuery.getTargetId() != null) {
      bool.filter(termQuery("targetId", taskQuery.getTargetId()));
    }
    if (taskQuery.getAffairId() != null) {
      bool.filter(termQuery("affairId", taskQuery.getAffairId()));
    }
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withIndices(Suffix.indexNamePattern(TaskPO.class))
        .withQuery(bool)
        .withPageable(taskQuery.getPageRequest())
        .withSourceFilter(DefaultFetchSource.defaultId())
        .build();
    return template.queryForPage(searchQuery, TaskPO.class);
  }
}
