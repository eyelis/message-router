package com.eyelis.messagerouter;

import com.eyelis.messagerouter.infrastructure.adapter.web.MessageController;
import com.eyelis.messagerouter.infrastructure.adapter.web.PartnerController;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest
@Testcontainers
class MessagerouterApplicationTests {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:15-alpine"
    );

    @Container
    private static final KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:latest"))
            .withExposedPorts(9092);

    @Autowired
    private MessageController messageController;

    @Autowired
    private PartnerController partnerController;


    @BeforeAll
    static void beforeAll() {
        postgres.start();
        kafkaContainer.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
        kafkaContainer.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Test
    void testServerStarts() {
        Assertions.assertThat(messageController).isNotNull();
        Assertions.assertThat(partnerController).isNotNull();
    }

}
