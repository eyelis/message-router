package com.eyelis.messagerouter.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
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
@ConfigurationProperties(prefix = "spring.kafka")
public class KafkaConsumerConfig {

    private String bootstrapServers;

    private String topic;

    private Consumer consumer;

    private final Map<String, KafkaGroupConfig.GroupConfig> groups;

    @Autowired
    public KafkaConsumerConfig(KafkaGroupConfig kafkaGroupConfig) {
        this.groups = kafkaGroupConfig.getGroups();
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
        final KafkaGroupConfig.GroupConfig groupConfig = groups.get(groupId);
        factory.setConsumerFactory(consumerFactory(groupConfig.getGroupId()));
        factory.setConcurrency(groupConfig.getConcurrency());
        factory.setRecordFilterStrategy(record -> {
            String key = record.key();
            return key == null || !key.startsWith(groupId);
        });
        return factory;
    }

    private ConsumerFactory<String, String> consumerFactory(String groupId) {
        final Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumer.clientId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumer.keyDeserializer);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumer.valueDeserializer);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Getter
    @Setter
    public static class Consumer {
        private String clientId;
        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
    }


}