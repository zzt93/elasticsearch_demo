package cn.superid.search.impl.entities.user.file;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface FileRepo extends ElasticsearchRepository<FilePO, String>, FileCustom {

}