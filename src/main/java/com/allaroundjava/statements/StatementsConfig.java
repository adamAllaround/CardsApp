package com.allaroundjava.statements;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;

@Configuration
@EnableKafka
@ComponentScan(basePackages = {"com.allaroundjava.statements"})
class StatementsConfig {

}