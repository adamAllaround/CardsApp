package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public abstract class CreditCardEvent implements DomainEvent {
    private final UUID eventID = UUID.randomUUID();

    abstract CardNumber getCardId();

    public String getAggregateId() {
        return getCardId().getCardNumber();
    }

    @Override
    public UUID getEventId() {
        return eventID;
    }

    @Getter
    @AllArgsConstructor
    static class Failure extends CreditCardEvent{
        private final CardNumber cardId;
        private final String message;
        private final Instant when;
        static Failure failNow(CardNumber cardId, String message) {
            return new Failure(cardId, message, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class LimitAssigned extends CreditCardEvent {
        CardNumber cardId;
        BigDecimal limit;
        Instant when;

        static LimitAssigned assignLimitNow(CardNumber cardId, BigDecimal limit) {
            return new LimitAssigned(cardId, limit, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class CardActivated extends CreditCardEvent {
        CardNumber cardId;
        Instant when;

        static CardActivated activateNow(CardNumber cardId) {
            return new CardActivated(cardId, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class MoneyRepaid extends CreditCardEvent {
        CardNumber cardId;
        BigDecimal amountRepaid;
        Instant when;

        static MoneyRepaid repayNow(CardNumber cardId, BigDecimal amount) {
            return new MoneyRepaid(cardId, amount, Instant.now());
        }
    }

    @Getter
    @AllArgsConstructor
    static class MoneyWithdrawn extends CreditCardEvent {
        CardNumber cardId;
        BigDecimal amountWithdrawn;
        Instant when;

        static MoneyWithdrawn withdrawNow(CardNumber cardId, BigDecimal amount) {
            return new MoneyWithdrawn(cardId, amount, Instant.now());
        }
    }
}
