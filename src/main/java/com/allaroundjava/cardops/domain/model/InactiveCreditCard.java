package com.allaroundjava.cardops.domain.model;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static com.allaroundjava.cardops.domain.model.CreditCardEvent.CardActivated.activateNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.Failure.failNow;

@Getter
@RequiredArgsConstructor
public class InactiveCreditCard extends BaseCreditCard {
    private final CardNumber id;
    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        addEvent(failNow(this.id, "Cannot assign limit to inactive credit card"));
        return this;
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        addEvent(failNow(this.id, "Cannot repay money on inactive credit card"));
        return this;
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        addEvent(failNow(this.id, "Cannot withdraw from inactive credit card"));
        return this;
    }

    @Override
    public CreditCard activate() {
        addEvent(activateNow(this.id));
        return new ActiveCreditCard(id, BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
