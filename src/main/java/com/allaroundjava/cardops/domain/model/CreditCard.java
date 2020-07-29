package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.domain.DomainObject;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;

import java.math.BigDecimal;

public interface CreditCard extends DomainObject {
    CreditCard assignLimit(BigDecimal limit);

    CreditCard repayMoney(BigDecimal amount);

    CreditCard withdraw(BigDecimal amount);

    CreditCard activate();

    String getCardNumber();

    CreditCardSnapshot snapshot();

    static CreditCard active(String id, BigDecimal limit, BigDecimal currentAmount) {
        return new ActiveCreditCard(id, limit, currentAmount);
    }

    static CreditCard inactive(String id) {
        return new InactiveCreditCard(id);
    }
}
