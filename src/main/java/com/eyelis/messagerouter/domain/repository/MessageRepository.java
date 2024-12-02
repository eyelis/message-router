package com.eyelis.messagerouter.domain.repository;

import com.eyelis.messagerouter.domain.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    Message save(Message message);
    Optional<Message> findById(Long id);
    List<Message> findAll();
}
