package com.allaroundjava.cardops.domain.model;

import lombok.Getter;

@Getter
final class CardNumber {
    private final String cardNumber;

    private CardNumber(String cardNumber) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CardNumber that = (CardNumber) o;

        return cardNumber.equals(that.cardNumber);
    }

    @Override
    public int hashCode() {
        return cardNumber.hashCode();
    }
}
