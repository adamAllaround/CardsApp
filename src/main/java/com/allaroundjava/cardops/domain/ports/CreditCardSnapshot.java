package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CardNumber;

import java.math.BigDecimal;

public class CreditCardSnapshot {
    private CardNumber id;
    private BigDecimal currentAmount;
    private BigDecimal limit;
    private boolean active;

    private CreditCardSnapshot(CardNumber id, BigDecimal currentAmount, BigDecimal limit, boolean active) {
        this.id = id;
        this.currentAmount = currentAmount;
        this.limit = limit;
        this.active = active;
    }

    public CardNumber getId() {
        return id;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public static CreditCardSnapshot inactive(CardNumber id, BigDecimal currentAmount, BigDecimal limit) {
        return new CreditCardSnapshot(id, currentAmount, limit, false);
    }

    public static CreditCardSnapshot active(CardNumber id, BigDecimal currentAmount, BigDecimal limit) {
        return new CreditCardSnapshot(id, currentAmount, limit, true);
    }

    public boolean isActive() {
        return this.active;
    }
}
