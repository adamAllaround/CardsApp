package com.allaroundjava.cardops.adapters.messaging;

import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.domain.ports.EventsStorage;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryEventStorage implements EventsStorage {
    private final Set<DomainEvent> events = new HashSet<>();

    @Override
    public void save(DomainEvent event) {
        events.add(event);
    }

    @Override
    public Iterable<DomainEvent> toPublish() {
        return Collections.unmodifiableSet(events);
    }

    @Override
    public void remove(DomainEvent event) {
        events.remove(event);
    }
}
