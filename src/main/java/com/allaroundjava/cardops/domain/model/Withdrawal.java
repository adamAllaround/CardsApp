package com.allaroundjava.cardops.domain.model;


import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
public class Withdrawal {
    private Long id;
    private Long creditCardId;
    private final BigDecimal amount;
    private final Instant when;

    public Withdrawal(Long id, Long creditCardId, BigDecimal amount, Instant when) {
        this.id = id;
        this.creditCardId = creditCardId;
        this.amount = amount;
        this.when = when;
    }

    public Withdrawal(Long creditCardId, BigDecimal amount, Instant when) {
        this.creditCardId = creditCardId;
        this.amount = amount;
        this.when = when;
    }
}
