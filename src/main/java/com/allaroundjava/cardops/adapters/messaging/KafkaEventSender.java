package com.allaroundjava.cardops.adapters.messaging;

import com.allaroundjava.cardops.common.events.DomainEvent;
import com.allaroundjava.cardops.domain.ports.DomainEventSender;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

@RequiredArgsConstructor
public class KafkaEventSender implements DomainEventSender {
    private final KafkaTemplate<String, Object> template;

    private final String topicName;

    @Override
    public void send(DomainEvent event) {
        template.send(topicName, event.getEventId().toString(), event)
                .addCallback(new LoggingCallback());
    }

    private static class LoggingCallback implements ListenableFutureCallback<SendResult<String, Object>>  {

        @Override
        public void onFailure(Throwable throwable) {
            System.out.println(String.format("Unable to send message due to [%s]", throwable.getMessage()));
        }

        @Override
        public void onSuccess(SendResult<String, Object> result) {
            System.out.println(String.format("Successfully sent message %s with offset %d",result.getProducerRecord().key(),
                    result.getRecordMetadata().offset()));
        }
    }
}
