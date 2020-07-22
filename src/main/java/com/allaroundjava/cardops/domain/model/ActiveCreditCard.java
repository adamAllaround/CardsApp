package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import lombok.Getter;

import java.math.BigDecimal;

import static com.allaroundjava.cardops.domain.model.CreditCardEvent.Failure.failNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.LimitAssigned.assignLimitNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.MoneyRepaid.repayNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.MoneyWithdrawn.withdrawNow;

@Getter
public class ActiveCreditCard extends BaseCreditCard {
    private final BigDecimal limit;
    private final BigDecimal currentAmount;

    public ActiveCreditCard(CardNumber id, BigDecimal limit, BigDecimal currentAmount) {
        super(id);
        this.limit = limit;
        this.currentAmount = currentAmount;
    }

    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        addEvent(assignLimitNow(getId(), limit));
        return new ActiveCreditCard(getId(), limit, this.currentAmount);
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        if (amount.compareTo(currentAmount.abs()) > 0) {
            addEvent(failNow(getId(), "Cannot Repay more than is owed"));
            return this;
        }
        addEvent(repayNow(getId(), amount));
        return new ActiveCreditCard(getId(), this.limit, currentAmount.add(amount));
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        if (currentAmount.subtract(amount).compareTo(limit.negate()) < 0) {
            addEvent(failNow(getId(), "Cannot withdraw that amount"));
            return this;
        }
        addEvent(withdrawNow(getId(), amount));
        return new ActiveCreditCard(getId(), this.limit, currentAmount.subtract(amount));
    }

    @Override
    public CreditCard activate() {
        return this;
    }

    @Override
    public CreditCardSnapshot snapshot() {
        return CreditCardSnapshot.active(getId(), this.currentAmount, this.limit);
    }
}
