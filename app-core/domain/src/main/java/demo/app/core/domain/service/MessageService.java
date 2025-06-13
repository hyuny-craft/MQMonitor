package demo.app.core.domain.service;

import demo.app.core.domain.command.MessageCommand;
import demo.app.core.domain.event.EventPublishPort;
import demo.app.core.domain.model.Message;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final EventPublishPort eventPublishPort;

    public void sendMessage(List<MessageCommand> messages) {
        messages.forEach(command -> {
            eventPublishPort.publish(new Message(command.id(), command.payload()).toEvent());
        });
    }
}
