package cn.superid.search.entities;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

/**
 * @author zzt
 */
public class PageRequestDeserializer implements
    Converter<Map<String, Object>, PageRequest> {


  public PageRequest convert(Map<String, Object> value) {
    Sort sort;
    if (value.containsKey("sort")) {
      List<Map> orderList = (List<Map>) ((Map) value.get("sort")).get("orders");
      List<Order> orders = new ArrayList<>();
      for (Map map : orderList) {
        Order order = new Order(Direction.valueOf((String) map.get("direction")),
            (String) map.get("property"),
            NullHandling.valueOf((String) map.get("nullHandling")));
        if (((boolean) map.get("ignoreCase"))) {
          order.ignoreCase();
        }
        orders.add(order);
      }
      sort = Sort.by(orders);
    } else {
      sort = Sort.unsorted();
    }
    return PageRequest.of(((Integer) value.get("pageNumber")),
        ((Integer) value.get("pageSize")), sort);
  }

  public JavaType getInputType(TypeFactory typeFactory) {
    return typeFactory.constructMapLikeType(Map.class, String.class, Object.class);
  }

  public JavaType getOutputType(TypeFactory typeFactory) {
    return typeFactory.constructSimpleType(PageRequest.class, new JavaType[]{});
  }
}
