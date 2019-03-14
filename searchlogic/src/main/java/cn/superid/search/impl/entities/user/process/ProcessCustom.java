package cn.superid.search.impl.entities.user.process;

import cn.superid.search.entities.user.process.ProcessQuery;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author zzt
 */
public interface ProcessCustom {
  Page<ProcessPO> find(ProcessQuery query, Pageable pageable);

  Map<Long, Long> count(ProcessQuery query);

}
