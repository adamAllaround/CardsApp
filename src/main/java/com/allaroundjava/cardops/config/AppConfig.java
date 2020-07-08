package com.allaroundjava.cardops.config;

import com.allaroundjava.cardops.domain.ports.CreditCards;
import com.allaroundjava.cardops.domain.ports.Withdrawals;
import com.allaroundjava.cardops.domain.ports.Withdrawing;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"com.allaroundjava.cardops.adapters.db"})
@ComponentScan(basePackages = {"com.allaroundjava.cardops.adapters.db", "com.allaroundjava.cardops.adapters.api"})
public class AppConfig {
    @Bean
    Withdrawing createWithdrawing(CreditCards creditCards, Withdrawals withdrawals) {
        return new Withdrawing(creditCards, withdrawals);
    }
}
