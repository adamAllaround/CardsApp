package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class BaseCreditCard implements CreditCard {
    private final List<DomainEvent> events;

    public BaseCreditCard() {
        events = new ArrayList<>();
    }

    protected void addEvent(DomainEvent event) {
        events.add(event);
    }

    public List<DomainEvent> getEvents() {
        List<DomainEvent> result = Collections.unmodifiableList(events);
        events.clear();
        return result;
    }
}
