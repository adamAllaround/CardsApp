package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class RepaymentService {
    private final CreditCardsRepository creditCardsRepository;

    public Optional<CreditCard> repay(RepayCommand command) {
        return creditCardsRepository.findById(command.getCardId())
                .map(card -> card.repayMoney(command.getAmount()));
    }
}
