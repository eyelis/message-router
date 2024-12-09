package com.eyelis.messagerouter.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "kafka-listener")
public class KafkaConsumerConfig {


    private Map<String, GroupConfig> groups = new HashMap<>();

    @Data
    public static class GroupConfig {
        private String groupId;
        private int concurrency;
    }

    @Bean
    public Map<String, GroupConfig> groups() {
        return groups;
    }


    @Bean(name = "message")
    public ConcurrentKafkaListenerContainerFactory<String, String> messageContainerFactory() {
        return containerFactory("message");
    }

    @Bean(name = "alerting")
    public ConcurrentKafkaListenerContainerFactory<String, String> alertingContainerFactory() {
        return containerFactory("alerting");
    }

    @Bean(name = "notification")
    public ConcurrentKafkaListenerContainerFactory<String, String> notificationContainerFactory() {
        return containerFactory("notification");
    }

    private ConcurrentKafkaListenerContainerFactory<String, String> containerFactory(final String groupId) {
        ConcurrentKafkaListenerContainerFactory<String, String> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        final GroupConfig groupConfig = groups.get(groupId);
        factory.setConsumerFactory(consumerFactory(groupConfig.groupId));
        factory.setConcurrency(groupConfig.concurrency);
        factory.setRecordFilterStrategy(record -> {
            String key = record.key();
            return key == null || !key.startsWith(groupId);
        });
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory(String groupId) {
        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        return new DefaultKafkaConsumerFactory<>(props);
    }


}