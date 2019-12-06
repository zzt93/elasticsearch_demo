package cn.superid.search.impl;

import cn.superid.search.entities.EsField;
import cn.superid.search.entities.EsField.SearchType;
import cn.superid.search.entities.user.process.ProcessQuery;
import cn.superid.search.entities.user.process.ProcessQuery.QueryType;
import cn.superid.search.entities.user.role.RoleQuery;
import cn.superid.search.impl.entities.user.process.ProcessPO;
import cn.superid.search.impl.entities.user.process.ProcessRepo;
import cn.superid.search.impl.entities.user.role.RolePO;
import cn.superid.search.impl.entities.user.role.RoleRepo;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchdemoApplicationTests {

  @Autowired
  ProcessRepo processRepo;
  @Autowired
  RoleRepo roleRepo;

  @Test
  public void contextLoads() {
  }


  @Test
  public void processQuery() {
    //Search QueryController.queryProcessInner(..) with [ProcessQuery{
    // affairIds=[32000203], targetIds=null, annIds=null, adminTargetIds=null, adminAnnIds=null, adminServiceIds=null, roleIds=null,
    // startTime=0, endTime=1552563022112, states=null, templates=null, queryType=TYPE_INNER, sourceType=0, processIds=null}]
    ProcessQuery query = new ProcessQuery();
    query.setAffairIds(Lists.newArrayList(32000203L));
    query.setStartTime(0);
    query.setEndTime(1552563022112l);
    query.setQueryType(QueryType.TYPE_INNER);
    query.setSourceType(0);
    query.setPageRequest(new PageRequest(0, 20));
    PageRequest pageRequest = query.getPageRequest();

    Page<ProcessPO> res = processRepo
        .find(query, pageRequest);
    System.out.println(res.getTotalElements());
  }

  @Test
  public void searchTags() {
    PageRequest pageRequest = new PageRequest(0, 100);
    RoleQuery roleQuery = new RoleQuery();
    roleQuery.setAffairId(38800121L);
    roleQuery.setAllianceId(17000103L);
    roleQuery.setPageRequest(pageRequest);
//    PageVO<RoleVO> roleVOPageVO = searcherClient.queryRoleTags(roleQuery);
    EsField esField = new EsField();
    esField.setSearchType(SearchType.PREFIX);
    esField.setName("type");
    esField.setValue("T");
    roleQuery.setInTagsField(esField);
    Page<RolePO> roleVOPageVO = roleRepo.findByAll(roleQuery);
    System.out.println(roleVOPageVO);
  }

}
