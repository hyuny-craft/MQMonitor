package com.hyuny.mqmonitor.application;

import com.hyuny.mqmonitor.domain.Message;
import com.hyuny.mqmonitor.domain.port.out.StatusMessageClient;
import com.hyuny.mqmonitor.utils.TimerUtils;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MessageProcessor {
    private final StatusMessageClient statusClient;
    private final MeterRegistry registry;

    public MessageProcessor(StatusMessageClient statusMessageClient, MeterRegistry registry) {
        this.statusClient = statusMessageClient;
        this.registry = registry;
    }

    public void process(Message message) {
        try {
            if (message.id().equals("fail")) {
                throw new Exception("RuntimeException");
            }
            TimerUtils.timed(registry, "mqmonitor.message.processing", () -> {
                System.out.printf("처리됨 → ID: %s / 내용: %s%n", message.id(), message.payload());
                // Increment success counter with result tag
                registry.counter("mqmonitor.message.processing.count", "result", "SUCCESS").increment();
                statusClient.publishStatus(message.id(), "SUCCESS", "");
            });
        } catch (Exception e) {
            extracted(message);
        }
    }

    private void extracted(Message message) {
        System.out.printf("실패됨 → ID: %s / 내용: %s%n", message.id(), message.payload());
        statusClient.publishStatus(message.id(), "FAILURE", "RuntimeException");
        registry.counter("mqmonitor.message.processing.count", "result", "FAILURE").increment();
    }


}
