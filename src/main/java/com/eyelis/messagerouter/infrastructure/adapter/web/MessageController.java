package com.eyelis.messagerouter.infrastructure.adapter.web;

import com.eyelis.messagerouter.application.usecase.DeleteMessageUseCase;
import com.eyelis.messagerouter.application.usecase.GetMessageUseCase;
import com.eyelis.messagerouter.application.usecase.ListMessageUseCase;
import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageProducerRepository;
import com.eyelis.messagerouter.infrastructure.adapter.dto.MessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
@Slf4j
public class MessageController {

    private final GetMessageUseCase getMessageUseCase;
    private final DeleteMessageUseCase deleteMessageUseCase;
    private final ListMessageUseCase listMessageUseCase;

    private final MessageProducerRepository messageProducer;

    @Value("${spring.kafka.topic}")
    private String topic;

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        log.info(STR."Getting message by id [\{id}] ");
        Optional<Message> message = getMessageUseCase.execute(id);
        return message.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages() {
        log.info("Listing messages");
        return ResponseEntity.ok(listMessageUseCase.execute());
    }

    @PostMapping("send")
    public void sendMessage(@RequestBody MessageRequest request) {
        log.info(STR."Sending topic [\{topic}] request [\{request}] ");
        messageProducer.produce(topic, request.key(), request.message());
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        log.info(STR."Deleting message by id [\{id}] ");
        deleteMessageUseCase.execute(id);
    }
}
