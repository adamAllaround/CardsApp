package com.allaroundjava.cardops.adapters.db;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface CreditCardInternalRepository extends MongoRepository<CreditCardEntity, String> {
}
