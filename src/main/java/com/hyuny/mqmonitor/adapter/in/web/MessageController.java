package com.hyuny.mqmonitor.adapter.in.web;

import com.hyuny.mqmonitor.adapter.in.web.dto.MessageRequestDto;
import com.hyuny.mqmonitor.application.MessageCommandService;
import com.hyuny.mqmonitor.domain.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageCommandService messageService;

    public MessageController(MessageCommandService messageService) {
        this.messageService = messageService;
    }

    @PostMapping
    public ResponseEntity<Void> publish(@RequestBody List<MessageRequestDto> requests) {
        List<Message> messages = requests.stream()
                .map(req -> new Message(req.id(), req.payload()))
                .toList();
        messageService.publishMessages(messages);
        return ResponseEntity.accepted().build();
    }
}
