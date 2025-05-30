package com.hyuny.mqmonitor.consumer.adapter.in;

import com.hyuny.mqmonitor.common.application.port.out.CounterClient;
import com.hyuny.mqmonitor.common.application.port.out.StatusMessageProducer;
import com.hyuny.mqmonitor.common.domain.Message;
import com.hyuny.mqmonitor.common.metrics.TimerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageProcessor {
    private final StatusMessageProducer statusClient;
    private final TimerClient timer;
    private final CounterClient counter;

    public void process(Message message) {
        try {
            if (message.id().equals("fail")) {
                throw new Exception("RuntimeException");
            }
            timer.timed("mqmonitor.message.processing", () -> {
                System.out.printf("처리됨 → ID: %s / 내용: %s%n", message.id(), message.payload());
                // Increment success counter with result tag
                counter.increment("mqmonitor.message.processing.count", "result", "SUCCESS");
                statusClient.produce(message.id(), "SUCCESS", "");
            });
        } catch (Exception e) {
            extracted(message);
        }
    }

    private void extracted(Message message) {
        System.out.printf("실패됨 → ID: %s / 내용: %s%n", message.id(), message.payload());
        statusClient.produce(message.id(), "FAILURE", "RuntimeException");
        counter.increment("mqmonitor.message.processing.count", "result", "FAILURE");
    }
}
