package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.common.command.Result;
import com.allaroundjava.cardops.common.domain.DomainObject;
import com.allaroundjava.cardops.common.events.DomainEvents;
import com.allaroundjava.cardops.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class RepaymentService {
    private final CreditCardsRepository creditCardsRepository;
    private final DomainEventSender messageSender;

    @Transactional
    public Result repay(RepayCommand command) {
        return creditCardsRepository.findById(command.getCardId())
                .map(card -> card.repayMoney(command.getAmount()))
                .map(this::saveState)
                .map(DomainObject::getEvents)
                .map(this::informOthers)
                .filter(DomainEvents::hasSuccess)
                .map(events -> Result.SUCCESS)
                .orElse(Result.FAILURE);
    }

    private DomainEvents informOthers(DomainEvents domainEvents) {
        domainEvents.forEachConsume(messageSender::send);
        return domainEvents;
    }

    private CreditCard saveState(CreditCard creditCard) {
        creditCardsRepository.save(creditCard.snapshot());
        return creditCard;
    }
}
