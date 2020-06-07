package com.allaroundjava.cardops.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Getter
public class ActiveCreditCard implements CreditCard {
    private final Long id;
    private final BigDecimal limit;
    private final BigDecimal currentAmount;

    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        return new ActiveCreditCard(this.id, limit, this.currentAmount);
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        if(amount.compareTo(currentAmount.abs()) > 0) throw new CreditCardOperationException();

        return new ActiveCreditCard(this.id, this.limit, currentAmount.add(amount));
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        if(currentAmount.subtract(amount).compareTo(limit.negate()) < 0) throw new CreditCardOperationException();
        return new ActiveCreditCard(this.id, this.limit, currentAmount.subtract(amount));
    }

    @Override
    public CreditCard activate() {
        return this;
    }
}
