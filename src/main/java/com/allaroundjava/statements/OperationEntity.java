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
class OperationEntity {
    private String cardId;
    private BigDecimal amount;
    private LocalDateTime when;
    private String type;

    static OperationEntity from(Operation message) {
        OperationEntity entity = new OperationEntity();
        entity.setCardId(message.getAggregateId());
        entity.setAmount(message.getAmount());
        entity.setWhen(LocalDateTime.ofInstant(message.getWhen(), ZoneOffset.UTC));
        entity.setType(message.getType());
        return entity;
    }
}
