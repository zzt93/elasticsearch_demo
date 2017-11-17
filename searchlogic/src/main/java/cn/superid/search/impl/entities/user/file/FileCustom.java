package cn.superid.search.impl.entities.user.file;

import java.util.List;

/**
 * @author zzt
 */
public interface FileCustom {

  void updateFileName(FilePO file);

  List<FilePO> findByNameOrUploadRoleName(String info, Long allianceId,
      Long affairId);

}
