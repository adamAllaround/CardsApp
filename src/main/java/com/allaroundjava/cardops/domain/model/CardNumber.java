package com.allaroundjava.cardops.domain.model;

import lombok.Getter;

@Getter
public final class CardNumber {
    private final String cardNumber;

    CardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public static CardNumber from(String cardNumber) {
        if (enoughNumbers(cardNumber) && regexMatch(cardNumber)) {
            return new CardNumber(cardNumber);
        }
        throw new CreditCardCreationException("Invalid credit card number");
    }

    private static boolean enoughNumbers(String cardNumber) {
        return cardNumber.length() == 4; // just for illustration
    }

    private static boolean regexMatch(String cardNumber) {
        return true;
    }
}
