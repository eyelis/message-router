package com.eyelis.messagerouter.infrastructure.adapter.kafka;

import com.eyelis.messagerouter.domain.repository.MessageProducerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer implements MessageProducerRepository {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public void produce(final String topic, final String key, final String content) {
        log.info(STR."Sending message topic [\{topic}] key [\{key}] content [\{content}]");
        var future = kafkaTemplate.send(topic, key, content);
        future.whenComplete((sendResult, exception) -> {
            if (exception == null) {
                future.complete(sendResult);
            } else {
                future.completeExceptionally(exception);
            }
        });
    }
}
