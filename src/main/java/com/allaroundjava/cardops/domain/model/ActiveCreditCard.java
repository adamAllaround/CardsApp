package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvents;
import com.allaroundjava.cardops.domain.ports.CreditCardSnapshot;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.allaroundjava.cardops.domain.model.CreditCardEvent.Failure.failNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.LimitAssigned.assignLimitNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.MoneyRepaid.repayNow;
import static com.allaroundjava.cardops.domain.model.CreditCardEvent.MoneyWithdrawn.withdrawNow;

@Getter
class ActiveCreditCard extends BaseCreditCard {
    private final BigDecimal limit;
    private final BigDecimal currentAmount;

    ActiveCreditCard(String id, BigDecimal limit, BigDecimal currentAmount) {
        this(id, limit, currentAmount, new DomainEvents(new ArrayList<>()));
    }

    private ActiveCreditCard(String id, BigDecimal limit, BigDecimal currentAmount, DomainEvents domainEvents) {
        super(id);
        this.limit = limit;
        this.currentAmount = currentAmount;
        domainEvents.forEachConsume(this::addEvent);
    }

    @Override
    public CreditCard assignLimit(BigDecimal limit) {
        addEvent(assignLimitNow(getCardNumber(), limit));
        return new ActiveCreditCard(getCardNumber(), limit, this.currentAmount);
    }

    @Override
    public CreditCard repayMoney(BigDecimal amount) {
        if (amount.compareTo(currentAmount.abs()) > 0) {
            addEvent(failNow(getCardNumber(), "Cannot Repay more than is owed"));
            return this;
        }
        addEvent(repayNow(getCardNumber(), amount));
        return new ActiveCreditCard(getCardNumber(), this.limit, currentAmount.add(amount));
    }

    @Override
    public CreditCard withdraw(BigDecimal amount) {
        if (currentAmount.subtract(amount).compareTo(limit.negate()) < 0) {
            addEvent(failNow(getCardNumber(), "Cannot withdraw that amount"));
            return this;
        }
        addEvent(withdrawNow(getCardNumber(), amount));
        return new ActiveCreditCard(getCardNumber(), this.limit, currentAmount.subtract(amount), getEvents());
    }

    @Override
    public CreditCard activate() {
        return this;
    }

    @Override
    public CreditCardSnapshot snapshot() {
        return CreditCardSnapshot.active(getCardNumber(), this.currentAmount, this.limit);
    }
}
