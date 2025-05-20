package com.hyuny.mqmonitor.domain.port.out;

import com.hyuny.mqmonitor.domain.Message;

public interface MessageClient {
    void send(Message message);
}
