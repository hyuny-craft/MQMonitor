package com.hyuny.mqmonitor.application;

import com.hyuny.mqmonitor.domain.Message;
import com.hyuny.mqmonitor.domain.port.out.MessageClient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageCommandService {
    private final MessageClient messageClient;

    public MessageCommandService(MessageClient messageClient) {
        this.messageClient = messageClient;
    }

    public void publishMessages(List<Message> messages) {
        messages.forEach(messageClient::send);
    }
}
