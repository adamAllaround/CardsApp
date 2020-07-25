package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CardNumber;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class RepayCommand {
    private final CardNumber cardId;
    private final BigDecimal amount;

    public RepayCommand(CardNumber cardId, BigDecimal amount) {
        this.cardId = cardId;
        this.amount = amount;
    }

    String getCardNumber() {
        return cardId.getCardNumber();
    }
}
