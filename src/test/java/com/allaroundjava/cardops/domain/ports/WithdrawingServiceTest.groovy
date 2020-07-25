package com.allaroundjava.cardops.domain.ports

import com.allaroundjava.cardops.common.events.DomainEvent
import com.allaroundjava.cardops.domain.model.ActiveCreditCard
import com.allaroundjava.cardops.domain.model.CardNumber
import com.allaroundjava.cardops.domain.model.InactiveCreditCard
import spock.lang.Specification

class WithdrawingServiceTest extends Specification {

    private CreditCardsRepository repository = Mock()
    private DomainEventSender sender = Mock()

    private WithdrawingService withdrawingService = new WithdrawingService(repository, sender)


    def "When Withdrawal successful then messages sent"() {
        given: "A Withdrawal command"
        def cardNumber = new CardNumber("asdf")
        WithdrawCommand command = new WithdrawCommand(cardNumber, BigDecimal.TEN)
        and: "Successful Withdrawal"
        repository.findById(cardNumber.getCardNumber()) >> new ActiveCreditCard(cardNumber, BigDecimal.valueOf(100), BigDecimal.ZERO)
        when: "Reacting to the command"
        withdrawingService.withdraw(command)

        then: "Messages are sent"
        1 * sender.send(_ as DomainEvent)
    }

    def "When Withdrawal unsuccessful then no message sent"() {
        given: "A Withdrawal command"
        def cardNumber = new CardNumber("asdf")
        WithdrawCommand command = new WithdrawCommand(cardNumber, BigDecimal.TEN)

        and: "Unsuccessful Withdrawal"
        repository.findById(cardNumber.getCardNumber()) >> new InactiveCreditCard(cardNumber)

        when: "Reacting to the command"
        withdrawingService.withdraw(command)

        then: "No messages are propagated"
        0 * sender.send(_ as DomainEvent)
    }
}
