package cn.superid.search.impl.entities.user.process;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/21.
 */
public interface ProcessRepo extends ElasticsearchRepository<ProcessPO, String>,
    ProcessCustom {
}
