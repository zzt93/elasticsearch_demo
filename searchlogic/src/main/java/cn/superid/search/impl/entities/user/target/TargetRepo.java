package cn.superid.search.impl.entities.user.target;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zzt
 */
public interface TargetRepo extends ElasticsearchRepository<TargetPO, String>, TargetCustom {


}
