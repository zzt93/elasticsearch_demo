package cn.superid.search.impl.entities.user.file;

import cn.superid.search.entities.user.file.FileQuery;
import org.springframework.data.domain.Page;

/**
 * @author zzt
 */
public interface FileCustom {

  Page<FilePO> findByNameOrUploadRoleName(FileQuery query);

}
