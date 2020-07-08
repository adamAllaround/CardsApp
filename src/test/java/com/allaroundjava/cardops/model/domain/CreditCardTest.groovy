package com.allaroundjava.cardops.model.domain

import spock.lang.Specification

class CreditCardTest extends Specification {
    CreditCard creditCard

    void setup() {
        creditCard = new InactiveCreditCard()
    }

    def "when card not active then cannot assign limit"() {
        when:
        creditCard.assignLimit(BigDecimal.valueOf(100))
        then:
        thrown(CreditCardOperationException)
    }

    def "can assign limit"() {
        given:
        creditCard = creditCard.activate()
        when:
        creditCard.assignLimit(BigDecimal.valueOf(100))
        then:
        notThrown(Throwable)
    }

    def "when card not active then cannot withdraw"() {
        when:
        creditCard.withdraw(BigDecimal.TEN)
        then:
        thrown(CreditCardOperationException)
    }

    def "when more than limit then cannot withdraw"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
        when:
        creditCard.withdraw(BigDecimal.valueOf(11))
        then:
        thrown(CreditCardOperationException)
    }

    def "can withdraw"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
        when:
        creditCard.withdraw(BigDecimal.TEN)
        then:
        notThrown(Throwable)
    }


    def "cannot repay more than limit"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
                .withdraw(BigDecimal.TEN)
        when:
        creditCard.repayMoney(BigDecimal.valueOf(12))
        then:
        thrown(CreditCardOperationException)
    }

    def "can repay"() {
        given:
        creditCard = creditCard.activate()
                .assignLimit(BigDecimal.TEN)
                .withdraw(BigDecimal.TEN)
        when:
        creditCard.repayMoney(BigDecimal.valueOf(8))
        then:
        notThrown(Throwable)
    }
}
