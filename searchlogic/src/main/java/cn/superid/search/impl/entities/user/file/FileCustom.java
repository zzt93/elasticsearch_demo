package cn.superid.search.impl.entities.user.file;

import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface FileCustom {


  Page<FilePO> findByNameOrUploadRoleName(String info, Long allianceId,
      Long affairId);

}
