package com.allaroundjava.cardops.domain.model;


import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
public class Withdrawal {
    private final UUID id = UUID.randomUUID();
    private final CardNumber creditCardId;
    private final BigDecimal amount;
    private final Instant when;

    public Withdrawal(CardNumber creditCardId, BigDecimal amount, Instant when) {
        this.creditCardId = creditCardId;
        this.amount = amount;
        this.when = when;
    }
}
