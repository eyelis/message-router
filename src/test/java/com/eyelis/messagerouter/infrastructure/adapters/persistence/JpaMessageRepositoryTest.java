package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.infrastructure.adapters.entity.MessageEntity;
import com.eyelis.messagerouter.infrastructure.adapters.persistence.mapper.MessageMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JpaMessageRepositoryTest {

    @Mock
    SpringJpaMessageRepository repository;

    @InjectMocks
    JpaMessageRepository jpaRepository;

    @Captor
    ArgumentCaptor<MessageEntity> entityCaptor;

    @Test
    void shouldCreateMessage() {

        //given / arrange
        final Message message = new Message(null, "key", "Message", LocalDateTime.now());
        final MessageEntity entity = MessageMapper.INSTANCE.toEntity(message);
        when(repository.save(any(MessageEntity.class))).thenReturn(entity);

        // when / act
        final Message result = jpaRepository.save(message);

        // then / assert
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(message);

        verify(repository).save(entityCaptor.capture());
        assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entityCaptor.getValue());
    }

    @Test
    void shouldRetrieveAllMessages() {

        //given / arrange
        final LocalDateTime date = LocalDateTime.now();
        final List<MessageEntity> entities = List.of(
                new MessageEntity(1L, "key", "Message1", date),
                new MessageEntity(2L, "key", "Message2", date)
        );
        when(repository.findAll()).thenReturn(entities);

        // when / act
        final List<Message> result = jpaRepository.findAll();

        // then / assert
        assertThat(result).hasSize(2);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(entities
                        .stream()
                        .map(MessageMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
                );

        verify(repository).findAll();
    }
}
