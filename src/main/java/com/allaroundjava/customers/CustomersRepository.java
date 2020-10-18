package com.allaroundjava.customers;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CustomersRepository extends MongoRepository<CustomerEntity, String> {
}
