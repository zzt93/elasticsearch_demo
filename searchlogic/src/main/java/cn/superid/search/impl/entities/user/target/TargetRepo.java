package cn.superid.search.impl.entities.user.target;

import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author zzt
 */
public interface TargetRepo extends ElasticsearchRepository<TargetPO, String> {

  List<TargetPO> findByNameAndAffairIdIn(String name, List<Long> affairIds);

}
