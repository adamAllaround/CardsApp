package com.allaroundjava.cardops.adapters.messaging;

import com.allaroundjava.cardops.domain.model.Withdrawal;
import com.allaroundjava.cardops.domain.ports.WithdrawalMessageSender;
import org.springframework.stereotype.Component;

@Component
public class RabbitMqWithrdawalMessageSender implements WithdrawalMessageSender {
    @Override
    public void sendMessageForNew(Withdrawal withdrawal) {

    }
}
