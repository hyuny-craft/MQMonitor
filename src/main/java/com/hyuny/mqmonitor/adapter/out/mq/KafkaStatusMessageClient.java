package com.hyuny.mqmonitor.adapter.out.mq;

import com.hyuny.mqmonitor.domain.port.out.StatusMessageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaStatusMessageClient implements StatusMessageClient {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.status-topic}")
    private String statusTopic;

    public KafkaStatusMessageClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publishStatus(String messageId, String status, String reason) {
        String statusPayload = String.format("{\"status\": \"%s\", \"reason\": \"%s\"}", status, reason);
        kafkaTemplate.send(statusTopic, messageId, statusPayload);
    }
}
