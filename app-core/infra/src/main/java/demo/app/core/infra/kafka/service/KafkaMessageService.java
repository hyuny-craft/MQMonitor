package demo.app.core.infra.kafka.service;


import demo.app.core.domain.event.DomainEvent;
import demo.app.core.domain.event.MessageSentEvent;
import demo.app.core.infra.kafka.adapter.out.CounterClient;
import demo.app.core.infra.metrics.TimerClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaMessageService {
    private final TimerClient timer;
    private final CounterClient counter;

    public void process(DomainEvent event) {
        MessageSentEvent _event = (MessageSentEvent) event;
        try {
            if (_event.id().equals("fail")) {
                throw new Exception("RuntimeException");
            }
            timer.timed("mqmonitor.message.processing", () -> {
                System.out.printf("처리됨 → ID: %s / 내용: %s%n", _event.id(), _event.reason());
                // Increment success counter with result tag
                counter.increment("mqmonitor.message.processing.count", "result", "SUCCESS");

                // TODO 수신된 메시지 처리 영역
//                statusClient.publish(new MessageSentEvent(event.id(), MessageStatus.SUCCESS, event.reason()));
            });
        } catch (Exception e) {
            processFail(_event);
        }
    }

    private void processFail(MessageSentEvent event) {
        System.out.printf("실패됨 → ID: %s / 내용: %s%n", event.id(), event.reason());
//        statusClient.publish(new MessageSentEvent(event.id(), "FAILURE", "RuntimeException"));
        counter.increment("mqmonitor.message.processing.count", "result", "FAILURE");
    }
}
