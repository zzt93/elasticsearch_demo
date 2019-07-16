package cn.superid.search.impl.entities.time.audit;

import cn.superid.search.entities.time.audit.AuditQuery;
import cn.superid.search.entities.time.audit.AuditUserQuery;
import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface AuditCustom {

  Page<AuditPO> findByQuery(AuditQuery auditQuery);

  Page<AuditPO> findByUserQuery(AuditUserQuery auditQuery);

}
