package cn.superid.search.impl.entities.user.user;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by zzt on 17/6/5.
 */
public interface UserAllianceRepo extends ElasticsearchRepository<AllianceUserPO, String> {


  Page<AllianceUserPO> findByUsernameAndAllianceId(String query, long allianceId, Pageable pageable);

  Page<AllianceUserPO> findByUsernameAndAllianceIdNot(String query, long allianceId, Pageable pageable);

}
