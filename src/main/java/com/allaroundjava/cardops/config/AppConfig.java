package com.allaroundjava.cardops.config;

import com.allaroundjava.cardops.adapters.messaging.InMemoryEventStorage;
import com.allaroundjava.cardops.adapters.messaging.KafkaMessageSender;
import com.allaroundjava.cardops.adapters.messaging.StoreAndForwardMessageSender;
import com.allaroundjava.cardops.domain.ports.CreditCardsRepository;
import com.allaroundjava.cardops.domain.ports.DomainEventSender;
import com.allaroundjava.cardops.domain.ports.EventsStorage;
import com.allaroundjava.cardops.domain.ports.WithdrawingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.allaroundjava.cardops.adapters.db", "com.allaroundjava.cardops.adapters.api", "com.allaroundjava.cardops.adapters.messaging"})
public class AppConfig {
    @Bean
    WithdrawingService createWithdrawing(CreditCardsRepository creditCardsRepository, DomainEventSender storeAndForwardMessageSender) {
        return new WithdrawingService(creditCardsRepository, storeAndForwardMessageSender);
    }

    @Bean
    DomainEventSender storeAndForwardMessageSender(EventsStorage storage) {
        return new StoreAndForwardMessageSender(new KafkaMessageSender(), storage);
    }

    @Bean
    EventsStorage eventsStorage() {
        return new InMemoryEventStorage();
    }
}
