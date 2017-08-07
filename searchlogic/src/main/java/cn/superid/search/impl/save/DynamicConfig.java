package cn.superid.search.impl.save;

import cn.superid.search.impl.save.rolling.Suffix;
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

