package cn.superid.search.impl.entities.user.file;

import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import cn.superid.search.impl.save.rolling.Suffix;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.action.index.IndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateQueryBuilder;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
public class FileRepoImpl implements FileCustom {

  @Autowired
  private Suffix suffix;
  @Autowired
  private ElasticsearchTemplate template;
  @Autowired
  private RoleRepo roleRepo;

  public void updateFileName(FilePO file) {
    IndexRequest indexRequest = new IndexRequest();
    indexRequest.source("name", file.getName());
    UpdateQuery updateQuery = new UpdateQueryBuilder()
        // class is used to infer `index` and `type`
        .withClass(FilePO.class)
        .withId(file.getId())
        // indexRequest will be used as `doc`
        .withIndexRequest(indexRequest).build();
    template.update(updateQuery);
  }

  @Override
  public List<FilePO> findByNameOrUploadRoleName(String info,
      Long allianceId, Long affairId) {
    suffix.setSuffix(String.valueOf(allianceId / RolePO.CLUSTER_SIZE));
    // TODO 17/9/26 combine two search
    List<RolePO> rolePOS = roleRepo.findByAffairIdAndTitle(affairId, info);

    List<String> ids = rolePOS.stream().map(RolePO::getId).collect(Collectors.toList());
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(
            boolQuery()
                .must(termQuery("affairId", allianceId))
                .should(wildcardQuery("name", wildcard(info)))
                .should(termsQuery("uploadRoleId", ids)))
        .withIndices(Suffix.indexName(FilePO.class, affairId / FilePO.CLUSTER_SIZE))
        .build();
    return template.queryForList(searchQuery, FilePO.class);
  }

}
