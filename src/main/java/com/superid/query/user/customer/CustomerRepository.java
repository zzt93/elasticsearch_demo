package com.superid.query.user.customer;

/**
 * Created by zzt on 17/5/26.
 */

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

    Customer findByFirstName(String firstName);

    List<Customer> findByLastName(String lastName);

}
