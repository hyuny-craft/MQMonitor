package com.hyuny.mqmonitor.common.application.port.out;

public interface StatusMessagePubllisher {
    void publishStatus(String messageId, String status, String reason);
}
