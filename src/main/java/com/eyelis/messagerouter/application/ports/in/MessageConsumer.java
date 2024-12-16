package com.eyelis.messagerouter.application.ports.in;

public interface MessageConsumer {

    void consumeMessage(String key, String content);

    void consumeAlerting(String key, String content);

    void consumeNotification(String key, String content);
}
