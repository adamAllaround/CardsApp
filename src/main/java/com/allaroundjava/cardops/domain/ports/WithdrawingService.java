package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.common.command.Result;
import com.allaroundjava.cardops.common.domain.DomainObject;
import com.allaroundjava.cardops.common.events.DomainEvents;
import com.allaroundjava.cardops.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
public class WithdrawingService {

    private final CreditCardsRepository creditCardsRepository;
    private final DomainEventSender messageSender;

    @Transactional
    public Result withdraw(WithdrawCommand withdrawCommand) {
        return creditCardsRepository.findById(withdrawCommand.getCardNumber())
                .map(creditCard -> creditCard.withdraw(withdrawCommand.getAmount()))
                .map(this::saveState)
                .map(DomainObject::getEvents)
                .filter(DomainEvents::hasSuccess)
                .map(this::informOthers)
                .map(events -> Result.SUCCESS)
                .orElse(Result.FAILURE);
    }

    private DomainEvents informOthers(DomainEvents events) {
        events.forEachConsume(messageSender::send);
        return events;
    }

    private CreditCard saveState(CreditCard creditCard) {
        creditCardsRepository.save(creditCard.snapshot());
        return creditCard;
    }
}