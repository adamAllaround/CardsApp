package com.allaroundjava.customers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;

interface EventSender {
    void send(NewCustomerEvent from);
}

@Log4j2
@RequiredArgsConstructor
class KafkaEventSender implements EventSender {
    private final KafkaTemplate<String, Object> template;
    private final String topicName;

    @Override
    public void send(NewCustomerEvent event) {
        template.send(topicName, event.getId(), event)
                .addCallback(new LoggingCallback());
    }

    private static class LoggingCallback implements ListenableFutureCallback<SendResult<String, Object>> {

        @Override
        public void onFailure(Throwable throwable) {
            log.warn(String.format("Unable to send message due to [%s]", throwable.getMessage()));
        }

        @Override
        public void onSuccess(SendResult<String, Object> result) {
            log.info(String.format("Successfully sent message %s with offset %d",result.getProducerRecord().key(),
                    result.getRecordMetadata().offset()));
        }
    }
}
