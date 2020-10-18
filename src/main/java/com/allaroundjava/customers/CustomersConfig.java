package com.allaroundjava.customers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
class CustomersConfig {
    @Bean
    CustomerService customerService(CustomersRepository customersRepository, EventSender eventSender) {
        return new CustomerService(customersRepository, eventSender);
    }

    @Bean
    EventSender eventSender(KafkaTemplate<String, Object> kafkaTemplate, @Value("${customers.topic}") String topic) {
        return new KafkaEventSender(kafkaTemplate, topic);
    }



}
