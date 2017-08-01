package cn.superid.search.impl.query.user.role;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import cn.superid.search.entities.RollingIndex;
import cn.superid.search.entities.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Created by zzt on 17/6/27.
 * <p>
 * The name of this class have to match the core repository interface or won't add customized
 *
 * @see RoleRepo
 * @see RoleRepoImpl
 * @see EnableElasticsearchRepositories#repositoryImplementationPostfix()
 */
public class RoleRepoImpl implements RoleCustom {

  private static final String EXCEPT_PREFIX = "-";
  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<Role> findRoleExcept(Long alliance, String query, Pageable pageable) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(matchQuery("title", query))
        .withIndices(RollingIndex.indexNamePattern(Role.class),
            EXCEPT_PREFIX + RollingIndex.indexName(Role.class, alliance))
        .build();
    return template.queryForPage(searchQuery, Role.class);
  }

  @Override
  public Page<Role> findRoleInterAlliance(String query, Pageable pageable) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(matchQuery("title", query))
        .withIndices(RollingIndex.indexNamePattern(Role.class))
        .build();
    return template.queryForPage(searchQuery, Role.class);
  }
}
