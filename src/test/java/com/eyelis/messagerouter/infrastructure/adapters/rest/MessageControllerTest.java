package com.eyelis.messagerouter.infrastructure.adapters.rest;

import com.eyelis.messagerouter.application.usecases.ListMessageUseCase;
import com.eyelis.messagerouter.domain.model.Message;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class MessageControllerTest {

    @Mock
    ListMessageUseCase useCase;
    @InjectMocks
    private MessageController controller;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldListMessage() throws Exception {
        // given / arrange
        List<Message> expectedMessages = List.of(
                new Message(1L, "key1", "Message1", LocalDateTime.now()),
                new Message(2L, "key2", "Message2", LocalDateTime.now())
        );

        when(useCase.execute()).thenReturn(expectedMessages);

        //when / act
        MvcResult result = mockMvc.perform(get("/api/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andReturn();

        final String json = result.getResponse().getContentAsString();
        final List<Message> actualMessages = objectMapper.readValue(json, new TypeReference<>() {
        });

        //then / assert
        Assertions.assertThat(actualMessages).isEqualTo(expectedMessages);

    }

}
