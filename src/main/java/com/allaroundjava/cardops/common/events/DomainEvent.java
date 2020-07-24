package com.allaroundjava.cardops.common.events;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {

    UUID getEventId();

    String getAggregateId();

    Instant getWhen();
}
