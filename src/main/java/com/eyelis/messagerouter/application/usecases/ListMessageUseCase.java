package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.MessageRepository;
import com.eyelis.messagerouter.domain.model.Message;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListMessageUseCase {

    private final MessageRepository messageRepository;

    public List<Message> execute() {
        return messageRepository.findAll();
    }
}
