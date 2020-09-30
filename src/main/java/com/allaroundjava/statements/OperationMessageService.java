package com.allaroundjava.statements;

import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;

@Component
@RequiredArgsConstructor
class OperationMessageService {
    private final OperationRepository repository;

    @KafkaListener(topics = "${cardops.topic}")
    public void grabWithdrawal(@Payload Operation message) {
        repository.save(OperationEntity.from(message));
    }
}

@Value
class Operation {
    private final String eventId;
    private final String aggregateId;
    private final Instant when;
    private final boolean failure;
    private final BigDecimal amount;
    private final String type;
}
