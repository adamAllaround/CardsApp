package com.allaroundjava.cardops.common.events;

public interface DomainEvents {
    void publish(DomainEvent event);
}
