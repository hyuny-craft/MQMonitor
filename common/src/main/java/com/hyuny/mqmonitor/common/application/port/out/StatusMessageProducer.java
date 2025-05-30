package com.hyuny.mqmonitor.common.application.port.out;

public interface StatusMessageProducer {
    void produce(String messageId, String status, String reason);
}
