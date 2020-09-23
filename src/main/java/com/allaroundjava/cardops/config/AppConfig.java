package com.allaroundjava.cardops.config;

import com.allaroundjava.cardops.adapters.messaging.InMemoryEventStorage;
import com.allaroundjava.cardops.adapters.messaging.KafkaEventSender;
import com.allaroundjava.cardops.adapters.messaging.StoreAndForwardMessageSender;
import com.allaroundjava.cardops.domain.ports.CreditCardsRepository;
import com.allaroundjava.cardops.domain.ports.DomainEventSender;
import com.allaroundjava.cardops.domain.ports.EventsStorage;
import com.allaroundjava.cardops.domain.ports.RepaymentService;
import com.allaroundjava.cardops.domain.ports.WithdrawingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ComponentScan(basePackages = {"com.allaroundjava.cardops.adapters.db", "com.allaroundjava.cardops.adapters.api", "com.allaroundjava.cardops.adapters.messaging"})
public class AppConfig {
    @Bean
    WithdrawingService createWithdrawing(CreditCardsRepository creditCardsRepository, DomainEventSender storeAndForwardMessageSender) {
        return new WithdrawingService(creditCardsRepository, storeAndForwardMessageSender);
    }

    @Bean
    RepaymentService createRepayment(CreditCardsRepository creditCardsRepository, DomainEventSender messageSender) {
        return new RepaymentService(creditCardsRepository, messageSender);
    }

    @Bean
    DomainEventSender messageSender(KafkaTemplate<String, Object> kafkaTemplate, EventsStorage storage, @Value("${cardops.topic}") String topic) {
        return new StoreAndForwardMessageSender(new KafkaEventSender(kafkaTemplate, topic), storage);
    }

    @Bean
    EventsStorage eventsStorage() {
        return new InMemoryEventStorage();
    }
}
