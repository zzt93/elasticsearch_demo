package cn.superid.search.impl.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zzt
 */
//@Deprecated
@Configuration
@EnableWebMvc
public class ResponseConfig implements WebMvcConfigurer {

  /**
   * @param converters converters to convert response
   * @see org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl
   * @see org.springframework.data.elasticsearch.core.FacetedPageImpl
   */
  @Override
  public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
    final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
    final ObjectMapper pageMapper = new ObjectMapper();
    pageMapper.setSerializationInclusion(Include.NON_NULL);
    converter.setObjectMapper(pageMapper);
    converters.add(converter);
  }


}
