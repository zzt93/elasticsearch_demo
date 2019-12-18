package cn.superid.search.impl.query.esUtil;

import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;

/**
 * @author zzt
 */
public class DefaultFetchSource {

  public static FetchSourceFilter defaultId() {
    return new FetchSourceFilter(new String[]{"_id"}, null);
  }

  public static FetchSourceFilter fields(String... fields) {
    return new FetchSourceFilter(fields, null);
  }

}
