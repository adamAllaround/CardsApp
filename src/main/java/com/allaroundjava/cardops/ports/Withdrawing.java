package com.allaroundjava.cardops.ports;

import com.allaroundjava.cardops.model.CreditCard;
import com.allaroundjava.cardops.model.Withdrawal;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional
public class Withdrawing {

    private final CreditCards creditCards;
    private final Withdrawals withdrawals;

    public Optional<Withdrawal> withdraw(WithdrawCommand withdrawCommand) {
        return creditCards.findById(withdrawCommand.getCardId())
                .map(creditCard -> creditCard.withdraw(withdrawCommand.getAmount()))
                .map(this::publish)
                .map(creditCard -> new Withdrawal(creditCard.getId(), withdrawCommand.getAmount(), Instant.now()))
                .map(this::publish);
    }

    private CreditCard publish(CreditCard creditCard) {
        creditCards.publish(creditCard);
        return creditCard;
    }

    private Withdrawal publish(Withdrawal withdrawal) {
        withdrawals.publish(withdrawal);
        return withdrawal;
    }

}