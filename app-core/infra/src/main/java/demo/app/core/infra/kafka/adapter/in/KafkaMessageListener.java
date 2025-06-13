package demo.app.core.infra.kafka.adapter.in;

import demo.app.core.infra.kafka.service.EventDispatcher;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageListener {
    private final EventDispatcher dispatcher;

    @KafkaListener(topics = "main-topic", groupId = "customer-group-1")
    public void consume(String messagePayload, @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        System.out.println("messageKey = " + messageKey);
        dispatcher.route(messagePayload, messageKey);
    }

}
