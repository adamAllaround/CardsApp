package com.allaroundjava.cardops.model.ports;

import com.allaroundjava.cardops.model.domain.Withdrawal;

import java.util.Collection;

public interface Withdrawals {
    void publish(Withdrawal withdrawal);

    Collection<Withdrawal> findAllByCardId(Long cardId);
}
