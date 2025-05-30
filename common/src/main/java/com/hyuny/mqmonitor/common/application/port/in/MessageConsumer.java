package com.hyuny.mqmonitor.common.application.port.in;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;

public interface MessageConsumer {
    @KafkaListener(topics = "${app.kafka.topic}", groupId = "customer-group-1")
    void consume(String messagePayload, @Header(KafkaHeaders.RECEIVED_KEY) String messageKey);
}
