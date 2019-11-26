package cn.superid.search.entities;

import java.util.List;
import lombok.Data;

/**
 * @author zzt
 */
@Data
public class EsField {

  public enum SearchType {
    EXACT, CONTAINS/*only for string*/, WORD_SEGMENTATION/*only for string*/
  }

  private SearchType searchType;
  private String name;
  private Object value;
  private List<Object> terms;

  public String getName(String prefix) {
    if (searchType == SearchType.EXACT) {
      return prefix + "." + name  + ".keyword";
    }
    return prefix + "." + name;
  }
}
