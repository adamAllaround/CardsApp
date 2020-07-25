package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CardNumber;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class WithdrawCommand {
    private final CardNumber cardId;
    private final BigDecimal amount;
}
