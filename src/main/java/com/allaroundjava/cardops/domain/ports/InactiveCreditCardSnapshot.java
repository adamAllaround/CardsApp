package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CardNumber;

import java.math.BigDecimal;

public class InactiveCreditCardSnapshot {
    private CardNumber id;

    public InactiveCreditCardSnapshot(CardNumber id, BigDecimal zero, BigDecimal zero1) {
        super();
        this.id = id;
    }
}
