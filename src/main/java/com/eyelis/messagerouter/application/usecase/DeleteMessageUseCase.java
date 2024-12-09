package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMessageUseCase {

    private final MessageRepository messageRepository;

    public void execute(Long id) {
        messageRepository.deleteById(id);
    }
}
