package com.allaroundjava.cardops.ports;

import com.allaroundjava.cardops.model.CreditCard;

import java.util.Optional;

public interface CreditCards {
    Optional<CreditCard> findById(Long id);
    void publish(CreditCard card);
}
