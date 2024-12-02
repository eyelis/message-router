package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateMessageUseCaseTest {

    @Mock
    MessageRepository repository;

    @InjectMocks
    CreateMessageUseCase useCase;

    @Captor
    ArgumentCaptor<Message> messageCaptor;

    @Test
    void shouldCreateMessage() {
        // given / arrange
        Message message = new Message(1L, "Message",  LocalDateTime.now());
        when(repository.save(any(Message.class))).thenReturn(message);

        // when / act
        Message result = useCase.execute(message.content(), message.timestamp());

        // then / assert
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(message);

        verify(repository).save(messageCaptor.capture());

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(messageCaptor.getValue());
    }
}
