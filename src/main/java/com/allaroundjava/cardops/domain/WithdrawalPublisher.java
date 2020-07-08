package com.allaroundjava.cardops.domain;

import com.allaroundjava.cardops.domain.model.Withdrawal;
import com.allaroundjava.cardops.domain.ports.WithdrawalMessageSender;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawalPublisher {
    final WithdrawalMessageSender withdrawalMessageSender;

    public void publishNew(Withdrawal withdrawal) {
        withdrawalMessageSender.sendMessageForNew(withdrawal);
    }


}
