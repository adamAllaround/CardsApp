package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.common.events.DomainEvent;

public interface DomainEventSender {
    void send(DomainEvent event);

}
