package cn.superid.search.impl.query;

import cn.superid.search.entities.PageVO;
import cn.superid.search.entities.wiki.WikiQuery;
import cn.superid.search.entities.wiki.WikiVO;
import cn.superid.search.impl.entities.VoAndPoConversion;
import cn.superid.search.impl.entities.wiki.Wiki;
import cn.superid.search.impl.entities.wiki.WikiRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zzt
 */
@RestController
@RequestMapping("/inner/wiki")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class WikiController {

  private final WikiRepo wikiRepo;

  @PostMapping("/")
  public PageVO<WikiVO> queryTitle(@RequestBody WikiQuery query) {
    PageRequest pageRequest = query.getPageRequest();
    Page<Wiki> res = wikiRepo.findByTitle(query.getQuery(), pageRequest);
    return new PageVO<>(res, VoAndPoConversion::toVO, query.getPageRequest());
  }

}
