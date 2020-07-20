package com.allaroundjava.cardops.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

import static com.allaroundjava.cardops.domain.model.CreditCardEvent.Failure.failNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.LimitAssigned.assignLimitNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.MoneyRepaid.repayNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.MoneyWithdrawn.withdrawNow;

@RequiredArgsConstructor
@Getter
public class ActiveCreditCard extends BaseCreditCard {
    private final CardNumber id;
    private final BigDecimal limit;
    private final BigDecimal currentAmount;

    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        addEvent(assignLimitNow(this.id, limit));
        return new ActiveCreditCard(this.id, limit, this.currentAmount);
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        if (amount.compareTo(currentAmount.abs()) > 0) {
            addEvent(failNow(this.id, "Cannot Repay more than is owed"));
            return this;
        }
        addEvent(repayNow(this.id, amount));
        return new ActiveCreditCard(this.id, this.limit, currentAmount.add(amount));
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        if (currentAmount.subtract(amount).compareTo(limit.negate()) < 0) {
            addEvent(failNow(this.id, "Cannot withdraw that amount"));
            return this;
        }
        addEvent(withdrawNow(this.id, amount));
        return new ActiveCreditCard(this.id, this.limit, currentAmount.subtract(amount));
    }

    @Override
    public CreditCard activate() {
        return this;
    }
}
