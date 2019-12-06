package cn.superid.search.entities;

import java.util.List;
import lombok.Data;

/**
 * @author zzt
 */
@Data
public class EsField {

  public enum SearchType {
    EXACT,
    PREFIX/*only for string*/, PREFIX_IGNORE_CASE/*only for string*/,
    CONTAINS/*only for string*/, CONTAINS_IGNORE_CASE/*only for string*/,
    WORD_SEGMENTATION/*only for string*/
  }

  private SearchType searchType;
  private String name;
  private Object value;
  private List<Object> terms;

  public String getName(String prefix) {
    if (searchType == SearchType.EXACT || searchType == SearchType.PREFIX || searchType == SearchType.CONTAINS) {
      return prefix + "." + name  + ".keyword";
    }
    return prefix + "." + name;
  }
}
