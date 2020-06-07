package com.allaroundjava.cardops.ports;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class WithdrawCommand {
    private final Long cardId;
    private final BigDecimal amount;
}
