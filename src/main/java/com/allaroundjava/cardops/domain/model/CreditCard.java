package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.domain.DomainObject;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;

import java.math.BigDecimal;

public interface CreditCard extends DomainObject {
    CreditCard assignLimit(BigDecimal limit);

    CreditCard repayMoney(BigDecimal amount);

    CreditCard withdraw(BigDecimal amount);

    CreditCard activate();

    CardNumber getId();

    CreditCardSnapshot snapshot();
}
