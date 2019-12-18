package cn.superid.search.impl.query.esUtil;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.nestedQuery;
import static org.elasticsearch.index.query.QueryBuilders.termsQuery;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.lucene.search.join.ScoreMode;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.NestedQueryBuilder;
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

  public static NestedQueryBuilder nestedRoleFilter(List<Long> roleIds) {
    BoolQueryBuilder roles = boolQuery().filter(termsQuery("roles.role_id", roleIds));
    return nestedQuery("roles", roles, ScoreMode.Avg);
  }
}
