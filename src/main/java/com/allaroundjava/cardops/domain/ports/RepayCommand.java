package com.allaroundjava.cardops.domain.ports;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Getter
@RequiredArgsConstructor
public class RepayCommand {
    private final String cardId;
    private final BigDecimal amount;


}
