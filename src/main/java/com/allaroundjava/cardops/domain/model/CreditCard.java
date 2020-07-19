package com.allaroundjava.cardops.domain.model;

import java.math.BigDecimal;

public interface CreditCard {
    CreditCard assignLimit(BigDecimal limit);

    CreditCard repayMoney(BigDecimal amount);

    CreditCard withdraw(BigDecimal amount);

    CreditCard activate();

    CardNumber getId();

    BigDecimal getCurrentAmount();

    BigDecimal getLimit();
}
