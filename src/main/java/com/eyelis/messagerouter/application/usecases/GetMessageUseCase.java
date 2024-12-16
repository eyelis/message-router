package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.MessageRepository;
import com.eyelis.messagerouter.domain.model.Message;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetMessageUseCase {

    private final MessageRepository messageRepository;

    public Optional<Message> execute(final Long id) {
        return messageRepository.findById(id);
    }
}
