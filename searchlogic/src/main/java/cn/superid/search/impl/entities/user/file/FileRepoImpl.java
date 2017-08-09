package cn.superid.search.impl.entities.user.file;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.matchQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import cn.superid.search.impl.entities.user.role.RolePO;
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

/**
 * @author zzt
 */
public class FileRepoImpl implements FileCustom {

  @Autowired
  private ElasticsearchTemplate template;

  @Override
  public void updateFileName(FilePO file) {
    IndexRequest indexRequest = new IndexRequest();
    indexRequest.source("name", file.getName());
    UpdateQuery updateQuery = new UpdateQueryBuilder().withId(file.getId()).withClass(FilePO.class)
        .withIndexRequest(indexRequest).build();
    template.update(updateQuery);
  }

  @Override
  public List<FilePO> findByNameOrUploadRoleName(String info, Long affairId) {
    SearchQuery role = new NativeSearchQueryBuilder()
        .withQuery(matchQuery("title", info))
        .withIndices(Suffix.indexName(RolePO.class, affairId))
        .build();
    List<RolePO> rolePOS = template.queryForList(role, RolePO.class);
    List<String> ids = rolePOS.stream().map(RolePO::getId).collect(Collectors.toList());
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(
            boolQuery().should(matchQuery("name", info)).should(termsQuery("uploadRoleId", ids)))
        .withIndices(Suffix.indexName(FilePO.class, affairId))
        .build();
    return template.queryForList(searchQuery, FilePO.class);

  }
}
