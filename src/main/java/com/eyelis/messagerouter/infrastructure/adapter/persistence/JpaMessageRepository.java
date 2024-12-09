package com.eyelis.messagerouter.infrastructure.adapter.persistence;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import com.eyelis.messagerouter.infrastructure.adapter.entity.MessageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMessageRepository implements MessageRepository {

    private final SpringJpaMessageRepository jpaRepository;

    public JpaMessageRepository(SpringJpaMessageRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Optional<Message> findById(Long id) {
        return jpaRepository.findById(id)
                .map(entity -> new Message(entity.id(), entity.key(), entity.content(), entity.timestamp()));
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public List<Message> findAll() {
        return jpaRepository.findAll().stream()
                .map(entity -> new Message(entity.id(), entity.key(), entity.content(), entity.timestamp()))
                .collect(Collectors.toList());
    }

    @Override
    public Message save(Message message) {
        MessageEntity entity = new MessageEntity(message.id(), message.key(), message.content(), message.timestamp());
        MessageEntity savedEntity = jpaRepository.save(entity);
        return new Message(savedEntity.id(), savedEntity.key(), savedEntity.content(), savedEntity.timestamp());
    }

}
