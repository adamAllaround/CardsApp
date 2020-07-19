package com.allaroundjava.cardops.domain.model;

public class CreditCardCreationException extends RuntimeException {
    public CreditCardCreationException(String reason) {
        super(reason);
    }
}
