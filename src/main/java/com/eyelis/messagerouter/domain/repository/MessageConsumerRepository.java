package com.eyelis.messagerouter.domain.repository;

public interface MessageConsumerRepository {

    void consumeMessage(String key, String content);

    void consumeAlerting(String key, String content);

    void consumeNotification(String key, String content);
}
