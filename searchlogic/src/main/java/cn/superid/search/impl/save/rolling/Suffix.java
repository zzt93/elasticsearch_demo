package cn.superid.search.impl.save.rolling;


import cn.superid.search.impl.entities.time.TimeBasedIndex;
import java.text.SimpleDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.util.ReflectionUtils;

/**
 * Created by zzt on 17/6/30.
 */
public class Suffix {

  private static final Logger logger = LoggerFactory.getLogger(Suffix.class);
  private ThreadLocal<String> suffixes;

  public Suffix(String init) {
    suffixes = ThreadLocal.withInitial(() -> init);
  }

  public static String indexNamePattern(Class<?> clazz) {
    clazzCheck(clazz);
    return clazz.getAnnotation(Document.class).indexName().split("#")[0] + "*";
  }

  public static String timeBasedPattern(Class<? extends TimeBasedIndex> clazz, long start,
      long end) {
    clazzCheck(clazz);
    String indexPrefix = clazz.getAnnotation(Document.class).indexName().split("#")[0];
    if (start == 0 || end == 0) {
      return indexPrefix + "*";
    } else {
      String timeFormat;
      int len;
      try {
        timeFormat = (String) ReflectionUtils.invokeMethod(clazz.getMethod("timeFormat"), null);
        len = (int) ReflectionUtils.invokeMethod(clazz.getMethod("timeFormatLen"), clazz.newInstance());
      } catch (NoSuchMethodException | IllegalAccessException | InstantiationException ignored) {
        logger.error("", ignored);
        return indexPrefix + "*";
      }
      SimpleDateFormat dateFormat = new SimpleDateFormat(timeFormat);
      char[] startC = dateFormat.format(start).toCharArray();
      char[] endC = dateFormat.format(end).toCharArray();
      int i;
      for (i = 0; i < startC.length; i++) {
        if (startC[i] != endC[i] || i >= len) {
          break;
        }
      }
      return indexPrefix + new String(startC, 0, i) + "*";
    }
  }

  private static void clazzCheck(Class<?> clazz) {
    if (clazz.getAnnotation(Document.class) == null) {
      throw new IllegalArgumentException();
    }
  }

  public static String indexName(Class<?> clazz, Object var) {
    clazzCheck(clazz);
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

