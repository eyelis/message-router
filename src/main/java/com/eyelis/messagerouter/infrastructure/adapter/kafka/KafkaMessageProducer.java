package com.eyelis.messagerouter.infrastructure.adapter.kafka;

import com.eyelis.messagerouter.domain.repository.MessageProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KafkaMessageProducer implements MessageProducerRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void produce(final String topic, final String content) {
        kafkaTemplate.send(topic, content);
    }
}
