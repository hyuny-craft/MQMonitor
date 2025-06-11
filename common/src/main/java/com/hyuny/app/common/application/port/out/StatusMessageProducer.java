package com.hyuny.app.common.application.port.out;

public interface StatusMessageProducer {
    void produce(String messageId, String status, String reason);
}
