package cn.superid.search.impl.entities.user.role;

import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.existsQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.EsField;
import cn.superid.search.entities.user.role.RoleQuery;
import cn.superid.search.impl.save.rolling.Suffix;
import java.util.List;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.stereotype.Component;

/**
 * Created by zzt on 17/6/27. <p> The name of this class have to match the core repository interface
 * or won't add customized
 *
 * @see RoleRepo
 * @see RoleRepoImpl
 * @see EnableElasticsearchRepositories#repositoryImplementationPostfix()
 */
@Component
public class RoleRepoImpl implements RoleCustom {

  private static final String EXCEPT_PREFIX = "-";
  private final ElasticsearchTemplate template;

  @Autowired
  public RoleRepoImpl(ElasticsearchTemplate template) {
    this.template = template;
  }

  @Override
  public Page<RolePO> findRoleExcept(Long alliance, String query, Pageable pageable) {
    BoolQueryBuilder bool = new BoolQueryBuilder()
        .mustNot(termQuery("user_id", 0))
        .mustNot(termQuery("allianceId", alliance))
        .must(wildcardQuery("title", wildcard(query)));
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(bool)
        .withIndices(Suffix.indexNamePattern(RolePO.class))
        .build();
    return template.queryForPage(searchQuery, RolePO.class);
  }


  @Override
  public List<RolePO> findByAffairIdAndTitle(Long allianceId, Long affairId, String title) {
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(
            boolQuery()
                .filter(termQuery("affairId", affairId))
                .filter(wildcardQuery("title", wildcard(title)))
        )
        .withIndices(Suffix.indexName(RolePO.class, allianceId / RolePO.CLUSTER_SIZE))
        .build();
    return template.queryForList(searchQuery, RolePO.class);
  }

  @Override
  public Page<RolePO> findByAll(RoleQuery query) {
    BoolQueryBuilder queryBuilder = boolQuery();

    if (query.getAffairId() != null) {
      boolQuery().filter(termQuery("affairId", query.getAffairId()));
    }
    if (query.getAllianceId() != null) {
      boolQuery().filter(termQuery("allianceId", query.getAffairId()));
    }

    addEsField(query, queryBuilder);

    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(queryBuilder)
        .withIndices(Suffix.indexNamePattern(RolePO.class))
        .build();
    return template.queryForPage(searchQuery, RolePO.class);
  }

  private void addEsField(RoleQuery query, BoolQueryBuilder queryBuilder) {
    EsField inTagsField = query.getInTagsField();
    if (inTagsField == null) {
      return;
    }
    String name = inTagsField.getName("inTags");
    queryBuilder.filter(existsQuery(name));
    switch (inTagsField.getSearchType()) {
      case EXACT:
        if (inTagsField.getValue() != null) {
          queryBuilder.must(termQuery(name, inTagsField.getValue()));
        } else {
          queryBuilder.must(termsQuery(name, inTagsField.getTerms()));
        }
        break;
      case CONTAINS:
        queryBuilder.must(wildcardQuery(name, wildcard(inTagsField.getValue().toString())));
        break;
      case WORD_SEGMENTATION:
        queryBuilder.must(matchQuery(name, inTagsField.getValue().toString()));
        break;
    }
  }
}
