package com.eyelis.messagerouter.infrastructure.adapter.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = {"test-topic"})
public class KafkaMessageConsumerTest {

    private static final String TEST_TOPIC = "test-topic";
    @Autowired
    private EmbeddedKafkaBroker embeddedKafkaBroker;

    @Test
    void testKafkaListener() {
        //given
        Map<String, Object> producerProps = KafkaTestUtils.producerProps(embeddedKafkaBroker);
        KafkaTemplate<String, String> kafkaTemplate = new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerProps));

        String testMessage = "Test Message !";
        kafkaTemplate.send(TEST_TOPIC, testMessage);

        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("test-group", "true", embeddedKafkaBroker);
        DefaultKafkaConsumerFactory<String, String> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProps);
        var consumer = consumerFactory.createConsumer();

        //when
        embeddedKafkaBroker.consumeFromAnEmbeddedTopic(consumer, TEST_TOPIC);

        //then
        ConsumerRecord<String, String> record = KafkaTestUtils.getSingleRecord(consumer, TEST_TOPIC);

        assertThat(record.value()).isEqualTo(testMessage);
    }
}
