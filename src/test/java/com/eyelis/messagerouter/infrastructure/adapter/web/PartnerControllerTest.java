package com.eyelis.messagerouter.infrastructure.adapter.web;

import com.eyelis.messagerouter.application.usecase.CreatePartnerUseCase;
import com.eyelis.messagerouter.domain.model.Direction;
import com.eyelis.messagerouter.domain.model.Flow;
import com.eyelis.messagerouter.domain.model.Partner;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(PartnerController.class)
@ExtendWith(MockitoExtension.class)
public class PartnerControllerTest {

    @Mock
    CreatePartnerUseCase useCase;
    @InjectMocks
    private PartnerController controller;

    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Test
    void shouldCreateMessage() throws Exception {
        // given / arrange
        Partner partnerRequest = new Partner(
                null,
                "type",
                "alias",
                Direction.INBOUND,
                "application",
                Flow.ALERTING,
                "description"
        );

        Partner partnerResponse = new Partner(
                1L,
                "type",
                "alias",
                Direction.INBOUND,
                "application",
                Flow.ALERTING,
                "description"
        );

        String requestBody = toJson(partnerRequest);

        when(useCase.execute(any(Partner.class))).thenReturn(partnerResponse);

        mockMvc.perform(post("/api/partners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(partnerResponse.id()))
                .andExpect(jsonPath("$.type").value(partnerResponse.type()))
                .andExpect(jsonPath("$.alias").value(partnerResponse.alias()))
                .andExpect(jsonPath("$.direction").value(partnerResponse.direction().name()))
                .andExpect(jsonPath("$.flow").value(partnerResponse.flow().name()))
                .andExpect(jsonPath("$.application").value(partnerResponse.application()))
                .andExpect(jsonPath("$.description").value(partnerResponse.description()))
                .andDo(print());
    }

    private String toJson(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}
