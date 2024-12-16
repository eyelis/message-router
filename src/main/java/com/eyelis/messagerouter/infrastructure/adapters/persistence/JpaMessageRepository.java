package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.application.ports.out.MessageRepository;
import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.infrastructure.adapters.persistence.mapper.MessageMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class JpaMessageRepository implements MessageRepository {

    private final SpringJpaMessageRepository jpaRepository;

    public JpaMessageRepository(final SpringJpaMessageRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Message save(final Message message) {
        return MessageMapper.INSTANCE.toDto(jpaRepository.save(MessageMapper.INSTANCE.toEntity(message)));
    }

    @Override
    public Optional<Message> findById(final Long id) {
        return jpaRepository.findById(id).map(MessageMapper.INSTANCE::toDto);
    }

    @Override
    public List<Message> findAll() {
        return jpaRepository.findAll().stream().map(MessageMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public void deleteById(final Long id) {
        jpaRepository.deleteById(id);
    }

}
