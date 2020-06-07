package com.allaroundjava.cardops.adapters.db;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
interface WithdrawalsInternalRepository extends CrudRepository<WithdrawalEntity, Long> {

    Collection<WithdrawalEntity> findAllByCreditCardId(Long creditCardId);
}
