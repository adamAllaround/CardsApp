package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.common.events.DomainEvent;

public interface EventsStorage {

    void save(DomainEvent event);

    Iterable<DomainEvent> toPublish();

    void remove(DomainEvent event);
}
