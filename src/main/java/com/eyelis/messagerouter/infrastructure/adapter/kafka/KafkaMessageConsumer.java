package com.eyelis.messagerouter.infrastructure.adapter.kafka;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageConsumerRepository;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
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
public class KafkaMessageConsumer implements MessageConsumerRepository {

    private final MessageRepository messageRepository;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "message")
    public void consumeMessage(@Header(KafkaHeaders.RECEIVED_KEY) String key, String content) {
        save(key, content);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "alerting")
    public void consumeAlerting(@Header(KafkaHeaders.RECEIVED_KEY) String key, String content) {
        save(key, content);
    }

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", containerFactory = "notification")
    public void consumeNotification(@Header(KafkaHeaders.RECEIVED_KEY) String key, String content) {
        save(key, content);
    }

    private void save(String key, String content) {
        log.info(STR."Message Received by the group \{key} : \{content}");
        messageRepository.save(new Message(null, key, content, LocalDateTime.now()));
    }
}
