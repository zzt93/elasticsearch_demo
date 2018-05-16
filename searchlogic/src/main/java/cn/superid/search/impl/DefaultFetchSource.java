package cn.superid.search.impl;

import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;

/**
 * @author zzt
 */
public class DefaultFetchSource {

  public static FetchSourceFilter defaultId() {
    return new FetchSourceFilter(new String[]{"_id"}, null);
  }

}
