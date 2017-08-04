package cn.superid.search.entities.time.announcement;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.databind.util.Converter;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * The class encapsulates the query of {@link Announcement}
 *
 * @author zzt
 * @see Announcement
 */
public class AnnouncementQuery {

  private List<Long> affairIds;
  private String query;
  private PageRequest pageRequest;

  public AnnouncementQuery() {
  }

  public AnnouncementQuery(List<Long> affairIds, String query,
      PageRequest pageRequest) {
    this.affairIds = affairIds;
    this.query = query;
    this.pageRequest = pageRequest;
  }

  public List<Long> getAffairIds() {
    return affairIds;
  }

  public void setAffairIds(List<Long> affairIds) {
    this.affairIds = affairIds;
  }

  public String getQuery() {
    return query;
  }

  public void setQuery(String query) {
    this.query = query;
  }

  public PageRequest getPageRequest() {
    return pageRequest;
  }

  @JsonDeserialize(converter = PageRequestDeserializer.class)
  public void setPageRequest(PageRequest pageRequest) {
    this.pageRequest = pageRequest;
  }

  private static class PageRequestDeserializer implements
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
}
