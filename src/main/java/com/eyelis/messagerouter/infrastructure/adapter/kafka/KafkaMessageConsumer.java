package com.eyelis.messagerouter.infrastructure.adapter.kafka;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageConsumerRepository;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class KafkaMessageConsumer implements MessageConsumerRepository {

    private final MessageRepository messageRepository;

    @Override
    @KafkaListener(topics = "${spring.kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(final String content) {
        messageRepository.save(new Message(null, content, LocalDateTime.now()));
    }
}
