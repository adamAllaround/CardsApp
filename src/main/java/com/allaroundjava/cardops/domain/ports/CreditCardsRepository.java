package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CreditCard;

import java.util.Optional;

public interface CreditCardsRepository {
    Optional<CreditCard> findById(String id);
    void save(CreditCardSnapshot card);
}
