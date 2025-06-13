package demo.app.api.user.adapter.in.web.dto;

import demo.app.core.domain.command.MessageCommand;

public record MessageRequestDto(String id, String payload) {
    public MessageCommand toCommand() {
        return new MessageCommand(id, payload);
    }
}
