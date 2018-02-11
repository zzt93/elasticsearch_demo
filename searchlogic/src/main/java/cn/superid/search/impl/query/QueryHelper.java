package cn.superid.search.impl.query;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zzt
 */
public class QueryHelper {


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
}
