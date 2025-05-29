package com.hyuny.mqmonitor.consumer.adapter.in;

import com.hyuny.mqmonitor.application.MessageProcessor;
import com.hyuny.mqmonitor.domain.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final MessageProcessor messageProcessor;


    @KafkaListener(topics = "${app.kafka.topic}", groupId = "customer-group-1")
    public void consume(String messagePayload, @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        Message message = new Message(messageKey, messagePayload);
        messageProcessor.process(message);
    }
}

