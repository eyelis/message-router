package com.eyelis.messagerouter.infrastructure.adapter.persistence;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.infrastructure.adapter.entity.MessageEntity;
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
        Message message = new Message(null, "Message", LocalDateTime.now());
        MessageEntity entity = new MessageEntity(null, message.content(), message.timestamp());
        when(repository.save(any(MessageEntity.class))).thenReturn(entity);

        // when / act
        Message result = jpaRepository.save(message);

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
        List<MessageEntity> entities = List.of(
                new MessageEntity(1L, "Message1", date),
                new MessageEntity(2L, "Message2", date)
        );
        when(repository.findAll()).thenReturn(entities);

        // when / act
        List<Message> result = jpaRepository.findAll();

        // then / assert
        assertThat(result).hasSize(2);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(entities
                        .stream()
                        .map(entity -> new Message(entity.id(), entity.content(), entity.timestamp()))
                        .collect(Collectors.toList())
                );

        verify(repository).findAll();
    }

}
