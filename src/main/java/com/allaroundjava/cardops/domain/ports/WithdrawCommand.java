package com.allaroundjava.cardops.domain.ports;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class WithdrawCommand {
    private final String cardId;
    private final BigDecimal amount;

    String getCardNumber() {
        return cardId;
    }
}
