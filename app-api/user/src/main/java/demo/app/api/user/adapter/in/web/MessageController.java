package demo.app.api.user.adapter.in.web;

import demo.app.api.user.adapter.in.web.dto.MessageRequestDto;
import demo.app.core.domain.command.MessageCommand;
import demo.app.core.domain.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping
    public ResponseEntity<Void> publish(@RequestBody List<MessageRequestDto> requests) {
        List<MessageCommand> messages = requests.stream()
                .map(MessageRequestDto::toCommand)
                .toList();
        messageService.sendMessage(messages);
        return ResponseEntity.accepted().build();
    }
}
