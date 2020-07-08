package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.CreditCard;
import com.allaroundjava.cardops.domain.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class WithdrawingService {

    private final CreditCardsRepository creditCardsRepository;
    private final WithdrawalsRepository withdrawalsRepository;
    private final WithdrawalMessageSender messageSender;


    public Optional<Withdrawal> withdraw(WithdrawCommand withdrawCommand) {
        return creditCardsRepository.findById(withdrawCommand.getCardId())
                .map(creditCard -> creditCard.withdraw(withdrawCommand.getAmount()))
                .map(this::publish)
                .map(creditCard -> new Withdrawal(creditCard.getId(), withdrawCommand.getAmount(), Instant.now()))
                .map(this::publish);
    }

    private CreditCard publish(CreditCard creditCard) {
        creditCardsRepository.save(creditCard);
        return creditCard;
    }

    private Withdrawal publish(Withdrawal withdrawal) {
        withdrawalsRepository.save(withdrawal);
        messageSender.sendMessageForNew(withdrawal);
        return withdrawal;
    }

}