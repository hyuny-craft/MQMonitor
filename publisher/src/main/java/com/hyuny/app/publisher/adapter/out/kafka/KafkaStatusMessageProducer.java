package com.hyuny.app.publisher.adapter.out.kafka;

import com.hyuny.app.common.application.port.out.StatusMessageProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaStatusMessageProducer implements StatusMessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.status-topic}")
    private String statusTopic;

    public KafkaStatusMessageProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void produce(String messageId, String status, String reason) {
        String statusPayload = String.format("{\"status\": \"%s\", \"reason\": \"%s\"}", status, reason);
        kafkaTemplate.send(statusTopic, messageId, statusPayload);
    }
}
