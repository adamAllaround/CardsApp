package com.allaroundjava.cardops.model;

import java.math.BigDecimal;

public interface CreditCard {
    CreditCard assignLimit(BigDecimal limit);

    CreditCard repayMoney(BigDecimal amount);

    CreditCard withdraw(BigDecimal amount);

    CreditCard activate();

    Long getId();

    BigDecimal getCurrentAmount();

    BigDecimal getLimit();
}
