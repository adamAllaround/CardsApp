package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CreditCard;

import java.util.Optional;

public interface CreditCards {
    Optional<CreditCard> findById(Long id);
    void publish(CreditCard card);
}