package com.allaroundjava.cardops.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class InactiveCreditCard implements CreditCard{
    private final Long id;
    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        throw new CreditCardOperationException();
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        throw new CreditCardOperationException();
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        throw new CreditCardOperationException();
    }

    @Override
    public CreditCard activate() {
        return new ActiveCreditCard(id, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public BigDecimal getCurrentAmount() {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal getLimit() {
        return BigDecimal.ZERO;
    }
}
