package com.allaroundjava.cardops.domain.ports;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RepayCommand {
    private final  Long cardId;
    private final BigDecimal amount;

    public RepayCommand(Long cardId, BigDecimal amount) {
        this.cardId = cardId;
        this.amount = amount;
    }
}
