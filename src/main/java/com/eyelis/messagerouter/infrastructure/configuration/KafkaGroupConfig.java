package com.eyelis.messagerouter.infrastructure.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "kafka-listener")
public class KafkaGroupConfig {

    private Map<String, GroupConfig> groups = new HashMap<>();

    @Getter
    @Setter
    public static class GroupConfig {
        private String groupId;
        private int concurrency;
    }
}