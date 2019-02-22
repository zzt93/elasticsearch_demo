package cn.superid.search.impl.entities.user.process;

import cn.superid.search.entities.user.process.ProcessQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface ProcessCustom {
  Page<ProcessPO> findInner(ProcessQuery query, Pageable pageable);
  Page<ProcessPO> findOuter(ProcessQuery query, Pageable pageable);
  Page<ProcessPO> findCreated(ProcessQuery query, Pageable pageable);

}
