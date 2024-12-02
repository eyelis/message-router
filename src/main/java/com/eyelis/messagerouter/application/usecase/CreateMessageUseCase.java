package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class CreateMessageUseCase {

     private final MessageRepository messageRepository;

    public Message execute(String content, LocalDateTime timestamp) {
        Message message = new Message(null, content, timestamp);
        return messageRepository.save(message);
    }
}
