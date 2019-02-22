package cn.superid.search.impl.entities.user.process;

import cn.superid.search.entities.user.process.ProcessQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.convert.ElasticsearchConverter;
import org.springframework.stereotype.Component;

/**
 * @author zzt
 */
@Component
public class ProcessRepoImpl implements ProcessCustom {

  @Autowired
  private ElasticsearchTemplate template;
  @Autowired
  private ElasticsearchConverter elasticsearchConverter;

  @Override
  public Page<ProcessPO> find(ProcessQuery query, Pageable pageable) {
    // TODO: 2019-02-22
    return null;
  }
}
