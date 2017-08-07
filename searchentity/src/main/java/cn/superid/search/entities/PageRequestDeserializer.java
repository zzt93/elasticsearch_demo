package cn.superid.search.entities;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * @author zzt
 */
public class PageRequestDeserializer implements
    Converter<Map<String, Object>, PageRequest> {


  public PageRequest convert(Map<String, Object> value) {
    return new PageRequest(((Integer) value.get("pageNumber")),
        ((Integer) value.get("pageSize")), (Sort) value.get("sort"));
  }

  public JavaType getInputType(TypeFactory typeFactory) {
    return typeFactory.constructMapLikeType(Map.class, String.class, Object.class);
  }

  public JavaType getOutputType(TypeFactory typeFactory) {
    return typeFactory.constructSimpleType(PageRequest.class, new JavaType[]{});
  }
}
