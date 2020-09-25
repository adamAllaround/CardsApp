package com.allaroundjava.statements;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawalRepository extends MongoRepository<WithdrawalEntity, String> {
    List<WithdrawalEntity> findByCardId(String cardId);
}
