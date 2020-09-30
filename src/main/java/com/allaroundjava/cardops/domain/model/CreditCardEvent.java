package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class CreditCardEvent implements DomainEvent {
    private final UUID eventID = UUID.randomUUID();
    protected final String type;

    abstract String getCardId();

    public String getAggregateId() {
        return getCardId();
    }

    @Override
    public UUID getEventId() {
        return eventID;
    }

    @Override
    public boolean isFailure() {
        return this instanceof Failure;
    }

    @Override
    public String getType() {
        return type;
    }

    @Getter
    static class Failure extends CreditCardEvent{
        private final String cardId;
        private final String message;
        private final Instant when;

        Failure(String cardId, String message, Instant when) {
            super("FAILURE");
            this.cardId = cardId;
            this.message = message;
            this.when = when;
        }

        static Failure failNow(String cardId, String message) {
            return new Failure(cardId, message, Instant.now());
        }
    }

    @Getter
    static class LimitAssigned extends CreditCardEvent {
        String cardId;
        BigDecimal limit;
        Instant when;

        LimitAssigned(String cardId, BigDecimal limit, Instant when) {
            super("LIMITASSIGNED");
            this.cardId = cardId;
            this.limit = limit;
            this.when = when;
        }

        static LimitAssigned assignLimitNow(String cardId, BigDecimal limit) {
            return new LimitAssigned(cardId, limit, Instant.now());
        }
    }

    @Getter
    static class CardActivated extends CreditCardEvent {
        String cardId;
        Instant when;

        CardActivated(String cardId, Instant when) {
            super("CARDACTIVATED");
            this.cardId = cardId;
            this.when = when;
        }

        static CardActivated activateNow(String cardId) {
            return new CardActivated(cardId, Instant.now());
        }
    }

    @Getter
    static class MoneyRepaid extends CreditCardEvent {
        String cardId;
        BigDecimal amount;
        Instant when;

        MoneyRepaid(String cardId, BigDecimal amount, Instant when) {
            super("MONEYREPAID");
            this.cardId = cardId;
            this.amount = amount;
            this.when = when;
        }

        static MoneyRepaid repayNow(String cardId, BigDecimal amount) {
            return new MoneyRepaid(cardId, amount, Instant.now());
        }
    }

    @Getter
    static class MoneyWithdrawn extends CreditCardEvent {
        String cardId;
        BigDecimal amount;
        Instant when;

        MoneyWithdrawn(String cardId, BigDecimal amount, Instant when) {
            super("MONEYWITHDRAWN");
            this.cardId = cardId;
            this.amount = amount;
            this.when = when;
        }

        static MoneyWithdrawn withdrawNow(String cardId, BigDecimal amount) {
            return new MoneyWithdrawn(cardId, amount, Instant.now());
        }
    }
}
