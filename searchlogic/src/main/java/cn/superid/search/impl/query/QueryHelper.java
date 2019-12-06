package cn.superid.search.impl.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.data.domain.PageRequest;

/**
 * @author zzt
 */
public class QueryHelper {


  public static final PageRequest EMPTY = PageRequest.of(0, 1);
  private static final Pattern PARAMETER_PLACEHOLDER = Pattern.compile("\\?(\\d+)");

  public static String replacePlaceholders(String input, String... var) {
    Matcher matcher = PARAMETER_PLACEHOLDER.matcher(input);
    String result = input;
    while (matcher.find()) {
      String group = matcher.group();
      int index = Integer.parseInt(matcher.group(1));
      result = result.replace(group, var[index]);
    }
    return result;
  }

  public static String wildcard(String input) {
    return "*" + input.replaceAll("([*?])", "\\\\$1") + "*";
  }

  public static String prefix(String input) {
    return input.replaceAll("([*?])", "\\\\$1") + "*";
  }
}
