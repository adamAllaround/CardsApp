package com.allaroundjava.statements;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Document(collection="operations")
@Setter
@Getter
class WithdrawalEntity {
    private String cardId;
    private BigDecimal amount;
    private LocalDateTime when;

    static WithdrawalEntity from(WithdrawalMessage message) {
        WithdrawalEntity entity = new WithdrawalEntity();
        entity.setCardId(message.getAggregateId());
        entity.setAmount(message.getAmountWithdrawn().negate());
        entity.setWhen(LocalDateTime.ofInstant(message.getWhen(), ZoneOffset.UTC));
        return entity;
    }
}
