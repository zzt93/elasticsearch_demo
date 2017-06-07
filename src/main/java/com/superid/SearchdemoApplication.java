package com.superid;

import com.superid.query.customer.Customer;
import com.superid.query.customer.CustomerRepository;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

//  @Configuration, @EnableAutoConfiguration and @ComponentScan
@SpringBootApplication
// If no base package is configured, it will use the one the configuration class resides in
@EnableElasticsearchRepositories(
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
public class SearchdemoApplication implements CommandLineRunner {

    private final CustomerRepository repository;
    private final ElasticsearchTemplate template;

    @Autowired
    public SearchdemoApplication(CustomerRepository repository, ElasticsearchTemplate template) {
        this.repository = repository;
        this.template = template;
    }


    public static void main(String[] args) throws Exception {
        SpringApplication.run(SearchdemoApplication.class, "--debug").close();
    }


    /**
     * ElasticsearchAutoConfiguration#elasticsearchClient:
     * Did not match:
     * - @ConditionalOnMissingBean (types: org.elasticsearch.client.Client; SearchStrategy: all) found bean 'elasticClient' (OnBeanCondition)
     */
    @Bean
    public ElasticsearchTemplate elasticsearchTemplate(Client client/* autowire from property file */) {
        return new ElasticsearchTemplate(client);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(template.getSetting("website"));

        this.repository.deleteAll();
        saveCustomers();
        fetchAllCustomers();
        fetchIndividualCustomers();
    }

    private void saveCustomers() {
        this.repository.save(new Customer("Alice", "Smith"));
        this.repository.save(new Customer("Bob", "Smith"));
    }

    private void fetchAllCustomers() {
        System.out.println("Customers found with findAll():");
        System.out.println("-------------------------------");
        for (Customer customer : this.repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();
    }

    private void fetchIndividualCustomers() {
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println("--------------------------------");
        System.out.println(this.repository.findByFirstName("Alice"));

        System.out.println("Customers found with findByLastName('Smith'):");
        System.out.println("--------------------------------");
        for (Customer customer : this.repository.findByLastName("Smith")) {
            System.out.println(customer);
        }
    }

}
