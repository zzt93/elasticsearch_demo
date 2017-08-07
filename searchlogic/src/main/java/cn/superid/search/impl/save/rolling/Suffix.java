package cn.superid.search.impl.save.rolling;


import org.springframework.data.elasticsearch.annotations.Document;

/**
 * Created by zzt on 17/6/30.
 */
public class Suffix {

  private ThreadLocal<String> suffixes;

  public Suffix(String init) {
    suffixes = ThreadLocal.withInitial(() -> init);
  }

  public static String indexNamePattern(Class<?> clazz) {
    return clazz.getAnnotation(Document.class).indexName().split("#")[0] + "*";
  }

  public static String indexName(Class<?> clazz, Object var) {
    return clazz.getAnnotation(Document.class).indexName().split("#")[0] + var.toString();
  }

  public void setSuffix(String suffix) {
    suffixes.set(suffix);
  }

  @Override
  public String toString() {
    return suffixes.get();
  }
}

