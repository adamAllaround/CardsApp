package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public abstract class CreditCardEvent implements DomainEvent {
    private final UUID eventID = UUID.randomUUID();

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

    @Getter
    @AllArgsConstructor
    static class Failure extends CreditCardEvent{
        private final String cardId;
        private final String message;
        private final Instant when;
        static Failure failNow(String cardId, String message) {
            return new Failure(cardId, message, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class LimitAssigned extends CreditCardEvent {
        String cardId;
        BigDecimal limit;
        Instant when;

        static LimitAssigned assignLimitNow(String cardId, BigDecimal limit) {
            return new LimitAssigned(cardId, limit, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class CardActivated extends CreditCardEvent {
        String cardId;
        Instant when;

        static CardActivated activateNow(String cardId) {
            return new CardActivated(cardId, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class MoneyRepaid extends CreditCardEvent {
        String cardId;
        BigDecimal amountRepaid;
        Instant when;

        static MoneyRepaid repayNow(String cardId, BigDecimal amount) {
            return new MoneyRepaid(cardId, amount, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class MoneyWithdrawn extends CreditCardEvent {
        String cardId;
        BigDecimal amountWithdrawn;
        Instant when;

        static MoneyWithdrawn withdrawNow(String cardId, BigDecimal amount) {
            return new MoneyWithdrawn(cardId, amount, Instant.now());
        }
    }
}
