package com.allaroundjava.cardops.adapters.messaging;

import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.domain.ports.DomainEventSender;

public class KafkaMessageSender implements DomainEventSender {
    @Override
    public void send(DomainEvent event) {

    }
}
