package com.hyuny.mqmonitor.application;

import com.hyuny.mqmonitor.common.application.port.out.MessageProducer;
import com.hyuny.mqmonitor.common.domain.Message;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageCommandService {
    private final MessageProducer messagePublisher;

    public MessageCommandService(MessageProducer messagePublisher) {
        this.messagePublisher = messagePublisher;
    }

    public void publishMessages(List<Message> messages) {
        messages.forEach(messagePublisher::produce);
    }
}
