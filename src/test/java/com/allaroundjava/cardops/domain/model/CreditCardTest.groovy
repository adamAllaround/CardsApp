package com.allaroundjava.cardops.domain.model


import spock.lang.Specification

class CreditCardTest extends Specification {
    CreditCard creditCard

    void setup() {
        creditCard = new InactiveCreditCard("1234")
    }

    def "when card not active then cannot assign limit"() {
        when:
        creditCard.assignLimit(BigDecimal.valueOf(100))
        then:
        creditCard.getEvents().forEachConsume({ assert it instanceof CreditCardEvent.Failure })
    }

    def "can assign limit"() {
        given:
        creditCard = creditCard.activate()
        when:
        creditCard.assignLimit(BigDecimal.valueOf(100))
        then:
        creditCard.getEvents().forEachConsume({ it instanceof CreditCardEvent.LimitAssigned })

    }

    def "when card not active then cannot withdraw"() {
        when:
        creditCard.withdraw(BigDecimal.TEN)
        then:
        creditCard.getEvents().forEachConsume({ it instanceof CreditCardEvent.Failure })
    }

    def "when more than limit then cannot withdraw"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
        when:
        creditCard.withdraw(BigDecimal.valueOf(11))
        then:
        creditCard.getEvents().forEachConsume({ it instanceof CreditCardEvent.Failure })
    }

    def "can withdraw"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
        when:
        creditCard.withdraw(BigDecimal.TEN)
        then:
        creditCard.getEvents().forEachConsume({ it instanceof CreditCardEvent.MoneyWithdrawn })
    }


    def "cannot repay more than limit"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
                .withdraw(BigDecimal.TEN)
        when:
        creditCard.repayMoney(BigDecimal.valueOf(12))
        then:
        creditCard.getEvents().forEachConsume({ it instanceof CreditCardEvent.Failure })
    }

    def "can repay"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
                .withdraw(BigDecimal.TEN)
        when:
        creditCard.repayMoney(BigDecimal.valueOf(8))
        then:
        creditCard.getEvents()
                .forEachConsume({ it instanceof CreditCardEvent.MoneyRepaid })
        and:
        creditCard.withdraw(BigDecimal.valueOf(8))
                .getEvents()
                .forEachConsume{ it instanceof CreditCardEvent.MoneyWithdrawn}
    }
}
