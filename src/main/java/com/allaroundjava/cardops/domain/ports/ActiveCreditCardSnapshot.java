package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CardNumber;

import java.math.BigDecimal;

public class ActiveCreditCardSnapshot {
    private CardNumber id;
    private BigDecimal currentAmount;
    private BigDecimal limit;

    public ActiveCreditCardSnapshot(CardNumber id, BigDecimal currentAmount, BigDecimal limit) {
        this.id = id;
        this.currentAmount = currentAmount;
        this.limit = limit;
    }
}
