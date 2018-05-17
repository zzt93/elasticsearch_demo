package cn.superid.search.impl.entities.user.file;

import static cn.superid.search.impl.query.QueryHelper.wildcard;
import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

import cn.superid.search.entities.user.file.FileQuery;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import cn.superid.search.impl.save.rolling.Suffix;
import com.google.common.base.Preconditions;
import java.util.List;
import java.util.stream.Collectors;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.DefaultResultMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
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
  @Autowired
  private ElasticsearchConverter elasticsearchConverter;

  @Override
  public Page<FilePO> findByNameOrUploadRoleName(FileQuery query) {
    Preconditions.checkNotNull(query.getAllianceId(), "No allianceId provided");
    Preconditions.checkNotNull(query.getAffairId(), "No affairId provided");
    Preconditions.checkNotNull(query.getFileSetId(), "No fileSetId provided");

    // TODO 17/9/26 combine two search
    List<RolePO> rolePOS = roleRepo.findByAffairIdAndTitle(query.getAllianceId(),
        query.getAffairId(), query.getQuery());

    List<String> ids = rolePOS.stream().map(RolePO::getId).collect(Collectors.toList());
    SearchQuery searchQuery = new NativeSearchQueryBuilder()
        .withQuery(
            boolQuery()
                .filter(termQuery("affairId", query.getAffairId()))
                .filter(termQuery("fileSetId", query.getFileSetId()))
                .filter(
                    boolQuery()
                        .should(wildcardQuery("name", wildcard(query.getQuery())))
                        .should(termsQuery("uploaderRoleId", ids))))
        .withIndices(Suffix.indexName(FilePO.class, query.getAffairId() / FilePO.CLUSTER_SIZE))
        .withTypes(FilePO.types())
        .withSourceFilter(new FetchSourceFilter(new String[]{"_id", "type"}, null))
        .build();
    return template.queryForPage(searchQuery, FilePO.class,
        new DefaultResultMapper(elasticsearchConverter.getMappingContext()) {
          @Override
          public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz,
              Pageable pageable) {
            AggregatedPage<FilePO> res = super.mapResults(response, FilePO.class, pageable);
            List<FilePO> chunk = res.getContent();
            SearchHits hits = response.getHits();
            for (int i = 0; i < hits.getHits().length; i++) {
              SearchHit at = hits.getAt(i);
              chunk.get(i).setType(at.getType());
            }
            if (chunk.size() > 0) {
              return new AggregatedPageImpl(chunk);
            }
            return null;
          }
        });

  }

}
