package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.Withdrawal;

import java.util.Collection;

public interface WithdrawalsRepository {
    void save(Withdrawal withdrawal);

    Collection<Withdrawal> findAllByCardId(Long cardId);
}
