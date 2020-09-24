package com.allaroundjava.statements;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@ComponentScan(basePackages = {"com.allaroundjava.statements"})
class StatementsConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private String bootstrapServers;

    private Map<String, Object> kafkaProperties() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "group1");
        return props;
    }

    private ConsumerFactory<String, WithdrawalMessage> consumerFactory() {

        JsonDeserializer<WithdrawalMessage> deserializer = new JsonDeserializer<>(WithdrawalMessage.class);
        deserializer.addTrustedPackages("com.allaroundjava.cardops.*");
        deserializer.ignoreTypeHeaders();

        return new DefaultKafkaConsumerFactory<>(kafkaProperties(),
                new StringDeserializer(),
                deserializer);
    }

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, WithdrawalMessage>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, WithdrawalMessage> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

}