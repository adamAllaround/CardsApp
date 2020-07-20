package com.allaroundjava.cardops.common.domain;

import com.allaroundjava.cardops.common.events.DomainEvent;

import java.util.List;

public interface DomainObject {
    List<DomainEvent> getEvents();
}
