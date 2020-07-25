package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.common.domain.DomainObject;
import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.domain.model.CreditCard;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
public class WithdrawingService {

    private final CreditCardsRepository creditCardsRepository;
    private final DomainEventSender messageSender;

    @Transactional
    public void withdraw(WithdrawCommand withdrawCommand) {
        creditCardsRepository.findById(withdrawCommand.getCardId())
                .map(creditCard -> creditCard.withdraw(withdrawCommand.getAmount()))
                .map(this::saveState)
                .map(DomainObject::getEvents)
                .ifPresent(this::informOthers);
    }

    private void informOthers(List<DomainEvent> events) {
        events.forEach(messageSender::send);
    }

    private CreditCard saveState(CreditCard creditCard) {
        creditCardsRepository.save(creditCard.snapshot());
        return creditCard;
    }
}