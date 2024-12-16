package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.MessageRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMessageUseCase {

    private final MessageRepository messageRepository;

    public void execute(Long id) {
        messageRepository.deleteById(id);
    }
}
