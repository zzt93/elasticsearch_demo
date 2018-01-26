package cn.superid.search.impl.entities.user.warehouse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/27.
 */
public interface MaterialRepo extends ElasticsearchRepository<MaterialPO, String>, MaterialCustom {

  Page<MaterialPO> findByTagsIn(String[] tags, Pageable pageable);

}
