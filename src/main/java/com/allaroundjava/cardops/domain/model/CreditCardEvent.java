package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;
import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public interface CreditCardEvent extends DomainEvent {
    CardNumber getCardId();

    @Override
    default String getAggregateId() {
        return getCardId().getCardNumber();
    }

    @Value
    class Failure implements CreditCardEvent{
        UUID eventId = UUID.randomUUID();
        CardNumber cardId;
        String message;
        Instant when;
        static Failure failNow(CardNumber cardId, String message) {
            return new Failure(cardId, message, Instant.now());
        }
    }

    @Value
    class LimitAssigned implements CreditCardEvent {
        UUID eventId = UUID.randomUUID();
        CardNumber cardId;
        BigDecimal limit;
        Instant when;

        static LimitAssigned assignLimitNow(CardNumber cardId, BigDecimal limit) {
            return new LimitAssigned(cardId, limit, Instant.now());
        }
    }

    @Value
    class CardActivated implements CreditCardEvent {
        UUID eventId = UUID.randomUUID();
        CardNumber cardId;
        Instant when;

        static CardActivated activateNow(CardNumber cardId) {
            return new CardActivated(cardId, Instant.now());
        }
    }

    @Value
    class MoneyRepaid implements CreditCardEvent {
        UUID eventId = UUID.randomUUID();
        CardNumber cardId;
        BigDecimal amountRepaid;
        Instant when;

        static MoneyRepaid repayNow(CardNumber cardId, BigDecimal amount) {
            return new MoneyRepaid(cardId, amount, Instant.now());
        }
    }

    @Value
    class MoneyWithdrawn implements CreditCardEvent {
        UUID eventId = UUID.randomUUID();
        CardNumber cardId;
        BigDecimal amountWithdrawn;
        Instant when;

        static MoneyWithdrawn withdrawNow(CardNumber cardId, BigDecimal amount) {
            return new MoneyWithdrawn(cardId, amount, Instant.now());
        }
    }
}
