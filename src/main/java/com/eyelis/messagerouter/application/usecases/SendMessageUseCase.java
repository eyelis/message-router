package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.MessageProducer;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SendMessageUseCase {

    private final MessageProducer messageProducer;

    public void execute(final String topic, final String key, final String content) {
        var future = messageProducer.produce(topic, key, content);
        future.whenComplete((sendResult, exception) -> {
            if (exception == null) {
                future.complete(sendResult);
            } else {
                future.completeExceptionally(exception);
            }
        });
    }
}
