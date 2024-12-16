package com.eyelis.messagerouter.application.ports.out;

import java.util.concurrent.CompletableFuture;

public interface MessageProducer {
    CompletableFuture<Void> produce(String topic, String key, String content);
}
