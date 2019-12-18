package cn.superid.search.impl.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.FeignAutoConfiguration;
import org.springframework.cloud.netflix.feign.support.ResponseEntityDecoder;
import org.springframework.cloud.netflix.feign.support.SpringDecoder;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Configuration
@ConditionalOnClass({Feign.class, HttpMessageConverters.class})
@AutoConfigureBefore(FeignAutoConfiguration.class)
public class FeignClientConfig {

  @Bean
  public Decoder feignDecoder() {
    HttpMessageConverter jacksonConverter =
        new MappingJackson2HttpMessageConverter(customObjectMapper());
    HttpMessageConverters converters = new HttpMessageConverters(jacksonConverter);
    ObjectFactory<HttpMessageConverters> objectFactory = () -> converters;
    return new ResponseEntityDecoder(new SpringDecoder(objectFactory));
  }

  @Bean
  public Encoder feignEncoder() {
    HttpMessageConverter jacksonConverter =
        new MappingJackson2HttpMessageConverter(customObjectMapper());
    HttpMessageConverters converters = new HttpMessageConverters(jacksonConverter);
    ObjectFactory<HttpMessageConverters> objectFactory = () -> converters;
    return new SpringEncoder(objectFactory);
  }

  private ObjectMapper customObjectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.ALL, Visibility.NONE);
    mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // Customize as much as you want
    return mapper;
  }
}
