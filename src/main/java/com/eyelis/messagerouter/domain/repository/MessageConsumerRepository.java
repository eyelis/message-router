package com.eyelis.messagerouter.domain.repository;

public interface MessageConsumerRepository {
    void consume(String message);

}
