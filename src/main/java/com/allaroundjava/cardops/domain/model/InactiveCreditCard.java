package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import lombok.Getter;

import java.math.BigDecimal;

import static com.allaroundjava.cardops.domain.model.CreditCardEvent.CardActivated.activateNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.Failure.failNow;

@Getter
public class InactiveCreditCard extends BaseCreditCard {

    public InactiveCreditCard(CardNumber cardNumber) {
        super(cardNumber);
    }

    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        addEvent(failNow(getId(), "Cannot assign limit to inactive credit card"));
        return this;
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        addEvent(failNow(getId(), "Cannot repay money on inactive credit card"));
        return this;
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        addEvent(failNow(getId(), "Cannot withdraw from inactive credit card"));
        return this;
    }

    @Override
    public CreditCard activate() {
        addEvent(activateNow(getId()));
        return new ActiveCreditCard(getId(), BigDecimal.ZERO, BigDecimal.ZERO);
    }

    @Override
    public CreditCardSnapshot snapshot() {
        return CreditCardSnapshot.inactive(getId(), BigDecimal.ZERO, BigDecimal.ZERO);
    }
}
