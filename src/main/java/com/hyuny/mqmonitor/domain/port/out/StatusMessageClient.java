package com.hyuny.mqmonitor.domain.port.out;

public interface StatusMessageClient {
    void publishStatus(String messageId, String status, String reason);
}
