package com.hyuny.mqmonitor.adapter.out.mq;

import com.hyuny.mqmonitor.domain.Message;
import com.hyuny.mqmonitor.domain.port.out.MessageClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaMessageClient implements MessageClient {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value( "${app.kafka.topic}")
    private String topic;

    public KafkaMessageClient(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void send(Message message) {
        kafkaTemplate.send(topic, message.id(), message.payload());
    }
}
