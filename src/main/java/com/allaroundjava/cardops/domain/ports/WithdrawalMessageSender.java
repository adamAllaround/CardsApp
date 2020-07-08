package com.allaroundjava.cardops.domain.ports;

import com.allaroundjava.cardops.domain.model.Withdrawal;

public interface WithdrawalMessageSender {
    void sendMessageForNew(Withdrawal withdrawal);
}
