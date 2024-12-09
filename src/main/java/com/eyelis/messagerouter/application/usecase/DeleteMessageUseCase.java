package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DeleteMessageUseCase {

    private final MessageRepository messageRepository;
    public Optional<Message> execute(Long id) {
        return messageRepository.findById(id);
    }
}
