package com.hyuny.app.common.application.port.out;

import com.hyuny.app.common.domain.Message;

public interface MessageProducer {
    void produce(Message message);
}
