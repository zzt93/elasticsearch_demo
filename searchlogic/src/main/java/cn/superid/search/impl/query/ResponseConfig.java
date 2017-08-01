package cn.superid.search.impl.query;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author zzt
 */
@Deprecated
//@Configuration
//@EnableWebMvc
public class ResponseConfig extends WebMvcConfigurerAdapter {

  /**
   *
   * @see org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl
   * @see org.springframework.data.elasticsearch.core.FacetedPageImpl
   * @param converters converters to convert response
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    final ObjectMapper pageMapper = new ObjectMapper();
    pageMapper.setSerializationInclusion(Include.NON_NULL);
    pageMapper.addMixIn(Page.class, IgnoreMixIn.class);
    converter.setObjectMapper(pageMapper);
    converters.add(converter);
    super.configureMessageConverters(converters);
  }


}
