package com.allaroundjava.statements;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
class WithdrawalService {

    @KafkaListener(topics = "${cardops.topic}")
    public void grabWithdrawal(@Payload String message) {
        System.out.println("Received "+ message);
    }
}
