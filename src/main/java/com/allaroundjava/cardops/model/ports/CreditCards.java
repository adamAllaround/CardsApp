package com.allaroundjava.cardops.model.ports;

import com.allaroundjava.cardops.model.domain.CreditCard;

import java.util.Optional;

public interface CreditCards {
    Optional<CreditCard> findById(Long id);
    void publish(CreditCard card);
}
