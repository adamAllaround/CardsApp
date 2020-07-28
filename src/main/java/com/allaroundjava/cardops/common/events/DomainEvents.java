package com.allaroundjava.cardops.common.events;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class DomainEvents {
    private final List<DomainEvent> events;

    public boolean hasSuccess() {
        return events.stream().noneMatch(DomainEvent::isFailure);
    }

    public void forEachConsume(Consumer<DomainEvent> consumer) {
        events.forEach(consumer);
    }
}
