package com.allaroundjava.statements;

import lombok.Value;

import java.math.BigDecimal;
import java.time.Instant;

@Value
public class WithdrawalMessage {
    private final String eventId;
    private final String aggregateId;
    private final Instant when;
    private final boolean failure;
    private final BigDecimal amountWithdrawn;
}
