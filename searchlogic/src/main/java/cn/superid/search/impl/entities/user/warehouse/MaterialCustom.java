package cn.superid.search.impl.entities.user.warehouse;

import cn.superid.search.entities.ScrollQuery;
import cn.superid.search.entities.user.warehouse.MaterialQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.SearchResultMapper;

/**
 * @author zzt
 */
public interface MaterialCustom {

  Page<MaterialPO> findByAllInfo(MaterialQuery info, Pageable pageable,
      SearchResultMapper mapper);

  Page<MaterialPO> findByAllInfo(ScrollQuery query);
}
