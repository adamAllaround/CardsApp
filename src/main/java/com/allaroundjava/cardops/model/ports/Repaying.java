package com.allaroundjava.cardops.model.ports;

import com.allaroundjava.cardops.model.domain.CreditCard;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class Repaying {
    private final CreditCards creditCards;

    public Optional<CreditCard> repay(RepayCommand command) {
        return creditCards.findById(command.getCardId())
                .map(card -> card.repayMoney(command.getAmount()));
    }
}
