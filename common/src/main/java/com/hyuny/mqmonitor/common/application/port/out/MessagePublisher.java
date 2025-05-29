package com.hyuny.mqmonitor.common.application.port.out;

import com.hyuny.mqmonitor.common.domain.Message;

public interface MessagePublisher {
    void send(Message message);
}
