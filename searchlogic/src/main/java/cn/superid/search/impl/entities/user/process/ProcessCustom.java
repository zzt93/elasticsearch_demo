package cn.superid.search.impl.entities.user.process;

import cn.superid.search.entities.user.process.ProcessCountVO;
import cn.superid.search.entities.user.process.ProcessQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface ProcessCustom {
  Page<ProcessPO> find(ProcessQuery query, Pageable pageable);

  ProcessCountVO count(ProcessQuery query);

  Page<ProcessPO> findMyProcess(ProcessQuery query, Pageable pageable);
}
