package cn.superid.search.impl.entities.announcement;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zzt
 */
public class MapUtil {

  public static Map map(Object k, Object v) {
    HashMap<Object, Object> m = Maps.newHashMap();
    m.put(k, v);
    return m;
  }

}
