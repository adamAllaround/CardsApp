package com.allaroundjava.cardops.adapters.messaging;

import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.domain.ports.DomainEventSender;
import com.allaroundjava.cardops.domain.ports.EventsStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@AllArgsConstructor
public class StoreAndForwardMessageSender implements DomainEventSender {
    private final DomainEventSender eventSender;
    private final EventsStorage eventsStorage;

    @Override
    public void send(DomainEvent event) {
        eventsStorage.save(event);
    }

    @Transactional
    public void publishAllOnInterval() {
        eventsStorage.toPublish().forEach(this::publishSingle);
    }

    private void publishSingle(DomainEvent event) {
        eventSender.send(event);
        eventsStorage.remove(event);
    }
}
