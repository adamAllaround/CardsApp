package com.allaroundjava.cardops.ports;

import com.allaroundjava.cardops.model.Withdrawal;

import java.util.Collection;

public interface Withdrawals {
    void publish(Withdrawal withdrawal);

    Collection<Withdrawal> findAllByCardId(Long cardId);
}
