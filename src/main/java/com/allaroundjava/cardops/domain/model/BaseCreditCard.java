package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.common.events.DomainEvents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

abstract class BaseCreditCard implements CreditCard {
    private final List<DomainEvent> events;
    private final CardNumber id;

    BaseCreditCard(CardNumber id) {
        this.id = id;
        events = new ArrayList<>();
    }

    void addEvent(DomainEvent event) {
        events.add(event);
    }

    public DomainEvents getEvents() {
        DomainEvents result = new DomainEvents(Collections.unmodifiableList(events));
        events.clear();
        return result;
    }

    @Override
    public CardNumber getId() {
        return this.id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseCreditCard that = (BaseCreditCard) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
