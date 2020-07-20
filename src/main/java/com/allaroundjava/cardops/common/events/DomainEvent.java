package com.allaroundjava.cardops.common.events;

import java.time.Instant;

public interface DomainEvent {

    Long getEventId();

    String getAggregateId();

    Instant getWhen();
}
