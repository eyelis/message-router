package com.eyelis.messagerouter.infrastructure.adapters.messaging;

import com.eyelis.messagerouter.application.ports.out.MessageProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaMessageProducer implements MessageProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public CompletableFuture<Void> produce(final String topic, final String key, final String content) {
        log.info(STR."Sending message topic [\{topic}] key [\{key}] content [\{content}]");
        return kafkaTemplate.send(topic, key, content)
                .toCompletableFuture()
                .thenApply(result -> null);

    }
}
