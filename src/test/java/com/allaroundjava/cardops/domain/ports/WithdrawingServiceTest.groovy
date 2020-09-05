package com.allaroundjava.cardops.domain.ports

import com.allaroundjava.cardops.common.command.Result
import com.allaroundjava.cardops.common.events.DomainEvent
import com.allaroundjava.cardops.domain.model.CreditCard
import spock.lang.Specification

class WithdrawingServiceTest extends Specification {
    private static final String CARD_NUM = "ASDF"

    private CreditCardsRepository repository = Mock()
    private DomainEventSender sender = Mock()

    private WithdrawingService withdrawingService = new WithdrawingService(repository, sender)


    def "When Withdrawal successful then messages sent"() {
        given: "A Withdrawal command"

        WithdrawCommand command = new WithdrawCommand(CARD_NUM, BigDecimal.TEN)
        and: "Successful Withdrawal"
        repository.findById(CARD_NUM) >>Optional.of(CreditCard.active(CARD_NUM, BigDecimal.valueOf(100), BigDecimal.ZERO))
        when: "Reacting to the command"
        def result = withdrawingService.withdraw(command)

        then: "Messages are sent"
        result == Result.SUCCESS
        1 * repository.save(_ as CreditCardSnapshot)
        1 * sender.send(_ as DomainEvent)
    }

    def "When Withdrawal unsuccessful then no message sent"() {
        given: "A Withdrawal command"
        WithdrawCommand command = new WithdrawCommand(CARD_NUM, BigDecimal.TEN)

        and: "Unsuccessful Withdrawal"
        repository.findById(CARD_NUM) >> Optional.of(CreditCard.inactive(CARD_NUM))

        when: "Reacting to the command"
        def result = withdrawingService.withdraw(command)

        then: "No messages are propagated"
        result == Result.FAILURE
        0 * sender.send(_ as DomainEvent)
    }
}
