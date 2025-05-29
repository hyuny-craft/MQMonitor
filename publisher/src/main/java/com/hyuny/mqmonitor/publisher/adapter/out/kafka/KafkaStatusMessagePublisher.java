package com.hyuny.mqmonitor.publisher.adapter.out.kafka;

import com.hyuny.mqmonitor.common.application.port.out.StatusMessagePubllisher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaStatusMessagePublisher implements StatusMessagePubllisher {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.status-topic}")
    private String statusTopic;

    public KafkaStatusMessagePublisher(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishStatus(String messageId, String status, String reason) {
        String statusPayload = String.format("{\"status\": \"%s\", \"reason\": \"%s\"}", status, reason);
        kafkaTemplate.send(statusTopic, messageId, statusPayload);
    }
}
