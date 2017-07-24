package cn.superid.search.impl.query.user.role;

import cn.superid.search.entities.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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
    @Autowired
    private ElasticsearchTemplate template;

    @Override
    public Page<Role> findRoleExcept(Long alliance, String query, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", query))
                .withIndices("role_*", "-role_" + alliance)
                .build();
        return template.queryForPage(searchQuery, Role.class);
    }

    @Override
    public Page<Role> findRoleInterAlliance(String query, Pageable pageable) {
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(matchQuery("title", query))
                .withIndices("role_*")
                .build();
        return template.queryForPage(searchQuery, Role.class);
    }
}
