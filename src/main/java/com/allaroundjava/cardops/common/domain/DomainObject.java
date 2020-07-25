package com.allaroundjava.cardops.common.domain;

import com.allaroundjava.cardops.common.events.DomainEvents;

public interface DomainObject {
    DomainEvents getEvents();
}
