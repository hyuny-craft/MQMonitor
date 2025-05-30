package com.hyuny.mqmonitor.common.application.port.out;

import com.hyuny.mqmonitor.common.domain.Message;

public interface MessageProducer {
    void produce(Message message);
}
