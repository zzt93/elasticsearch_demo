package cn.superid.search.entities;


import org.springframework.data.elasticsearch.annotations.Document;

/**
 * @author zzt
 */
public interface RollingIndex {

  static String indexNamePattern(Class<? extends RollingIndex> clazz) {
    return clazz.getAnnotation(Document.class).indexName().split("#")[0] + "*";
  }

  static String indexName(Class<? extends RollingIndex> clazz, Object var) {
    return clazz.getAnnotation(Document.class).indexName().split("#")[0] + var.toString();
  }

  String indexSuffix();


}
