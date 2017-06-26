package com.superid;

import com.superid.query.precreate.customer.Customer;
import com.superid.query.precreate.customer.CustomerRepository;
import com.superid.query.precreate.file.FileRepo;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.repository.query.QueryLookupStrategy;

// @Configuration, @EnableAutoConfiguration and @ComponentScan
@SpringBootApplication
// If no base package is configured, it will use the one the configuration class resides in
@EnableElasticsearchRepositories(
        queryLookupStrategy = QueryLookupStrategy.Key.CREATE_IF_NOT_FOUND)
public class SearchdemoApplication implements CommandLineRunner {

    @Autowired
    private CustomerRepository repository;
    @Autowired
    private FileRepo fileRepo;
    @Autowired
    private ElasticsearchTemplate template;


    public static void main(String[] args) throws Exception {
        SpringApplication.run(SearchdemoApplication.class, "--debug");
    }


    /**
     * ElasticsearchAutoConfiguration#elasticsearchClient:
     * Did not match:
     * - @ConditionalOnMissingBean (types: org.elasticsearch.client.Client; SearchStrategy: all) found bean 'elasticClient' (OnBeanCondition)
     */
//    @Bean
//    public ElasticsearchTemplate elasticsearchTemplate(Client client/* autowire from property file */) {
//        return new ElasticsearchTemplate(client);
//    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(template.getMapping("announcement", "announcement"));

//        fileRepo.findAllByTitleOrUploadRoleOrUploadUser("test", "test", "test", new PageRequest(1, 10));
//
//        this.repository.deleteAll();
//        saveCustomers();
//        fetchAllCustomers();
//        fetchIndividualCustomers();
    }

    private void saveCustomers() {
        this.repository.save(new Customer("Alice", "Smith"));
        this.repository.save(new Customer("Bob", "Smith"));
    }

    private void fetchAllCustomers() {
        System.out.println("-------------------------------");
        System.out.println("Customers found with findAll():");
        for (Customer customer : this.repository.findAll()) {
            System.out.println(customer);
        }
        System.out.println();
        System.out.println("-------------------------------");
    }

    private void fetchIndividualCustomers() {
        System.out.println("--------------------------------");
        System.out.println("Customer found with findByFirstName('Alice'):");
        System.out.println(this.repository.findByFirstName("Alice"));
        System.out.println("--------------------------------");

        System.out.println("--------------------------------");
        System.out.println("Customers found with findByLastName('Smith'):");
        for (Customer customer : this.repository.findByLastName("Smit")) {
            System.out.println(customer);
        }
        System.out.println("--------------------------------");
    }

}
