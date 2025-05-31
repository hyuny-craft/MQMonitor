package com.hyuny.mqmonitor.application;

import com.hyuny.mqmonitor.adapter.out.metrics.MicrometerTimerClient;
import com.hyuny.mqmonitor.common.adapter.out.metrics.MicrometerCounterClient;
import com.hyuny.mqmonitor.common.application.port.out.CounterClient;
import com.hyuny.mqmonitor.common.application.port.out.StatusMessageProducer;
import com.hyuny.mqmonitor.common.domain.Message;
import com.hyuny.mqmonitor.common.metrics.TimerClient;
import com.hyuny.mqmonitor.consumer.adapter.in.MessageProcessor;
import com.hyuny.mqmonitor.publisher.adapter.out.kafka.KafkaStatusMessageProducer;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class MessageProcessorTest {
    private StatusMessageProducer statusClient;
    private SimpleMeterRegistry registry;
    private TimerClient timer;
    private CounterClient counter;
    private MessageProcessor processor;

    @BeforeEach
    void setUp() {
        statusClient = mock(KafkaStatusMessageProducer.class);
        registry = new SimpleMeterRegistry();
        counter = new MicrometerCounterClient(registry);
        timer = new MicrometerTimerClient(registry);
        processor = new MessageProcessor(statusClient, timer, counter);
    }

    @Test
    void 성공_처리시_카운터증가_및_상태전송() {
        Message message = new Message("id-123", "ok");
        processor.process(message);

        Counter counter = registry.find("mqmonitor.message.processing.count")
                .tag("result", "SUCCESS").counter();

        assertThat(counter).isNotNull();

        registry.getMeters().forEach(meter -> System.out.println(meter.getId()));

        assertThat(counter.count()).isEqualTo(1.0);
        verify(statusClient).produce("id-123", "SUCCESS", "");
    }

    @Test
    void 실패_처리시_카운터증가_및_상태전송() {
        // given
        Message message = new Message("fail", "정상처리지만_전송실패");

        // when
        processor.process(message);

        // then
        Counter failureCounter = registry.find("mqmonitor.message.processing.count")
                .tags("result", "FAILURE").counter();

        if (failureCounter == null) {
            System.out.println("Available meters:");
            registry.getMeters().forEach(meter -> System.out.println(meter.getId()));
        }
        assertThat(failureCounter).isNotNull();
        assertThat(failureCounter.count()).isEqualTo(1.0);

        verify(statusClient).produce("fail", "FAILURE", "RuntimeException");
    }

}