package com.allaroundjava.cardops.domain.ports;

import java.math.BigDecimal;

public class CreditCardSnapshot {
    private String id;
    private BigDecimal currentAmount;
    private BigDecimal limit;
    private boolean active;

    private CreditCardSnapshot(String id, BigDecimal currentAmount, BigDecimal limit, boolean active) {
        this.id = id;
        this.currentAmount = currentAmount;
        this.limit = limit;
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public BigDecimal getCurrentAmount() {
        return currentAmount;
    }

    public BigDecimal getLimit() {
        return limit;
    }

    public static CreditCardSnapshot inactive(String id, BigDecimal currentAmount, BigDecimal limit) {
        return new CreditCardSnapshot(id, currentAmount, limit, false);
    }

    public static CreditCardSnapshot active(String id, BigDecimal currentAmount, BigDecimal limit) {
        return new CreditCardSnapshot(id, currentAmount, limit, true);
    }

    public boolean isActive() {
        return this.active;
    }
}
