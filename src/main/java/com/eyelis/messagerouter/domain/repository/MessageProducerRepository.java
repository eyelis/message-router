package com.eyelis.messagerouter.domain.repository;

public interface MessageProducerRepository {
    void produce(String topic, String key, String content);
}
