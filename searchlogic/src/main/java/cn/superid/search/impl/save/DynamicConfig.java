package cn.superid.search.impl.save;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by zzt on 17/6/30.
 */
@Configuration
public class DynamicConfig {

  @Bean
  Suffix suffix() {
    return new Suffix("");
  }
}

