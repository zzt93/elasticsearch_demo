package cn.superid.search.impl.entities.user.role;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

import cn.superid.search.impl.save.rolling.Suffix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

/**
 * Created by zzt on 17/6/27.
 * <p>
 * The name of this class have to match the core repository interface or won't add customized
 *
 * @see RoleRepo
 * @see RoleRepoImpl
 * @see EnableElasticsearchRepositories#repositoryImplementationPostfix()
 */
@Component
public class RoleRepoImpl implements RoleCustom {

  private static final String EXCEPT_PREFIX = "-";
  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public Page<RolePO> findRoleExcept(Long alliance, String query, Pageable pageable) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(matchQuery("title", query))
        .withIndices(Suffix.indexNamePattern(RolePO.class),
            EXCEPT_PREFIX + Suffix.indexName(RolePO.class, alliance))
        .build();
    return template.queryForPage(searchQuery, RolePO.class);
  }

  @Override
  public Page<RolePO> findRoleInterAlliance(String query, Pageable pageable) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(matchQuery("title", query))
        .withIndices(Suffix.indexNamePattern(RolePO.class))
        .build();
    return template.queryForPage(searchQuery, RolePO.class);
  }

  @Override
  public Page<RolePO> findByAll(String query) {
    return null;
  }
}
