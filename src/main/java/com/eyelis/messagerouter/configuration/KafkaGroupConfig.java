package com.eyelis.messagerouter.configuration;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Setter
@Getter
@Configuration
@ConfigurationProperties(prefix = "kafka-listener")
public class KafkaGroupConfig {

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

}