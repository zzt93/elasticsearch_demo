package cn.superid.search.impl;

import cn.superid.common.rest.client.BusinessClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

// @Configuration, @EnableAutoConfiguration and @ComponentScan
@SpringBootApplication
// If no base package is configured, it will use the one the configuration class resides in
@EnableElasticsearchRepositories(
    queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
@EnableWebMvc
@EnableDiscoveryClient
@EnableFeignClients(
    clients = {BusinessClient.class}
)
public class SearchdemoApplication implements CommandLineRunner {

  public static void main(String[] args) throws Exception {
    SpringApplication.run(SearchdemoApplication.class, args);
  }


  /**
   * ElasticsearchAutoConfiguration#elasticsearchClient: Did not match: - @ConditionalOnMissingBean
   * (types: org.elasticsearch.client.Client; SearchStrategy: all) found bean 'elasticClient'
   * (OnBeanCondition)
   */
//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(Client client/* autowire from property file */) {
//        return new ElasticsearchTemplate(client);
//    }
  @Override
  public void run(String... args) throws Exception {
  }


}
