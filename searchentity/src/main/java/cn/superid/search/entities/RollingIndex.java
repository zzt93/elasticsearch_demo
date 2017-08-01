package cn.superid.search.entities;


import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zzt
 */
public interface RollingIndex {

  String indexSuffix();

  static String clustersNameRegex(Class<? extends RollingIndex> clazz) {
    return clazz.getAnnotation(Document.class).indexName().split("#")[0] + "*";
  }
}
