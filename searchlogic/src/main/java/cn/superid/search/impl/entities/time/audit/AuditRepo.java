package cn.superid.search.impl.entities.time.audit;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface AuditRepo extends ElasticsearchRepository<AuditPO, String>, AuditCustom {


}
