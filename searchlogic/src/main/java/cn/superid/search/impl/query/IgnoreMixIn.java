package cn.superid.search.impl.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.data.elasticsearch.core.facet.FacetResult;

/**
 * @author zzt
 */
@Deprecated
public abstract class IgnoreMixIn {

  /**
   * Because this method will cause NPE, I have to ignore this field
   * @see FacetedPageImpl#getFacets()
   */
  @JsonIgnore
  abstract List<FacetResult> getFacets();

}
