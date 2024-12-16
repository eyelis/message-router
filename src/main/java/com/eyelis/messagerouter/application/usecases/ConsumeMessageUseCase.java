package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.MessageRepository;
import com.eyelis.messagerouter.domain.model.Message;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ConsumeMessageUseCase {

    private final MessageRepository messageRepository;

    public void execute(final Message message) {
        messageRepository.save(message);
    }
}
