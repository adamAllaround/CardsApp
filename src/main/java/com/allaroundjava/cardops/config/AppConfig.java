package com.allaroundjava.cardops.config;

import com.allaroundjava.cardops.domain.ports.CreditCards;
import com.allaroundjava.cardops.domain.ports.WithdrawalMessageSender;
import com.allaroundjava.cardops.domain.ports.WithdrawalsRepository;
import com.allaroundjava.cardops.domain.ports.WithdrawingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.allaroundjava.cardops.adapters.db"})
@ComponentScan(basePackages = {"com.allaroundjava.cardops.adapters.db", "com.allaroundjava.cardops.adapters.api", "com.allaroundjava.cardops.adapters.messaging"})
public class AppConfig {
    @Bean
    WithdrawingService createWithdrawing(CreditCards creditCards, WithdrawalsRepository withdrawalsRepository, WithdrawalMessageSender withdrawalMessageSender) {
        return new WithdrawingService(creditCards, withdrawalsRepository, withdrawalMessageSender);
    }
}
