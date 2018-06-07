package cn.superid.search.impl.entities.time.audit;

import cn.superid.search.entities.time.audit.AuditQuery;
import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface AuditCustom {

  Page<AuditPO> findByQuery(AuditQuery auditQuery);

}
