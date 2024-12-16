package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.MessageRepository;
import com.eyelis.messagerouter.domain.model.Message;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ConsumeMessageUseCase {

    private final MessageRepository messageRepository;

    public void execute(final String key, final String content) {
        messageRepository.save(new Message(null, key, content, LocalDateTime.now()));
    }
}
