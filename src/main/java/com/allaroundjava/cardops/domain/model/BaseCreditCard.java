package com.allaroundjava.cardops.domain.model;

import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.common.events.DomainEvents;

import java.util.ArrayList;
import java.util.List;

abstract class BaseCreditCard implements CreditCard {
    private final List<DomainEvent> events;
    private final CardNumber cardNumber;

    BaseCreditCard(String cardNumber) {
        this.cardNumber = CardNumber.from(cardNumber);
        events = new ArrayList<>();
    }

    void addEvent(DomainEvent event) {
        events.add(event);
    }

    public DomainEvents getEvents() {
        DomainEvents result = new DomainEvents(new ArrayList<>(events));
        events.clear();
        return result;
    }

    @Override
    public String getCardNumber() {
        return this.cardNumber.getCardNumber();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseCreditCard that = (BaseCreditCard) o;

        return cardNumber.equals(that.cardNumber);
    }

    @Override
    public int hashCode() {
        return cardNumber.hashCode();
    }
}
