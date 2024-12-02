package com.eyelis.messagerouter.domain.repository;

public interface MessageProducerRepository {
    void produce(final String topic, final String content);

}
