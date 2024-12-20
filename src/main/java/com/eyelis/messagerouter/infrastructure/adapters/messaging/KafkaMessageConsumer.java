package com.eyelis.messagerouter.infrastructure.adapters.messaging;

import com.eyelis.messagerouter.application.ports.in.MessageConsumer;
import com.eyelis.messagerouter.application.usecases.ConsumeMessageUseCase;
import com.eyelis.messagerouter.domain.model.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageConsumer implements MessageConsumer {

    private final ConsumeMessageUseCase consumeMessageUseCase;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "message")
    public void consumeMessage(@Header(KafkaHeaders.RECEIVED_KEY) final String key, final String content) {
        save(key, content);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "alerting")
    public void consumeAlerting(@Header(KafkaHeaders.RECEIVED_KEY) final String key, final String content) {
        save(key, content);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "notification")
    public void consumeNotification(@Header(KafkaHeaders.RECEIVED_KEY) final String key, final String content) {
        save(key, content);
    }

    private void save(final String key, final String content) {
        log.info(STR."Message Received by the group \{key} : \{content}");
        consumeMessageUseCase.execute(new Message(null, key, content, LocalDateTime.now()));
    }
}
