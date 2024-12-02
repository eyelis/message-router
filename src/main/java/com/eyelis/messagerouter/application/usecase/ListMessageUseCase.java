package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListMessageUseCase {

    private final MessageRepository messageRepository;

    public List<Message> execute() {
        return messageRepository.findAll();
    }
}
