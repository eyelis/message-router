package com.eyelis.messagerouter.application.ports.out;

import com.eyelis.messagerouter.domain.model.Message;

import java.util.List;
import java.util.Optional;

public interface MessageRepository {
    Optional<Message> save(Message message);

    Optional<Message> findById(Long id);

    List<Message> findAll();

    void deleteById(Long id);
}
