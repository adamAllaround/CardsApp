package com.allaroundjava.statements;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class WithdrawalMessageService {
    private final WithdrawalRepository repository;

    @KafkaListener(topics = "${cardops.topic}")
    public void grabWithdrawal(@Payload WithdrawalMessage message) {
        repository.save(WithdrawalEntity.from(message));
    }
}
