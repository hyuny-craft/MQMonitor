package demo.app.core.infra.kafka.adapter.in;

import demo.app.core.domain.event.DomainEvent;
import demo.app.core.domain.event.EventConsumeHandler;
import demo.app.core.domain.event.MessageSentEvent;
import demo.app.core.infra.kafka.service.KafkaMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MessageSentEventHandler implements EventConsumeHandler<MessageSentEvent> {
    private final KafkaMessageService messageService;

    @Override
    public void consume(MessageSentEvent event) {
        messageService.process(event);
    }

    @Override
    public Class<MessageSentEvent> getEventType() {
        return MessageSentEvent.class;
    }
}

