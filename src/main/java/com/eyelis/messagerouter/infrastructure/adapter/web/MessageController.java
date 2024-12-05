package com.eyelis.messagerouter.infrastructure.adapter.web;

import com.eyelis.messagerouter.application.usecase.CreateMessageUseCase;
import com.eyelis.messagerouter.application.usecase.GetMessageUseCase;
import com.eyelis.messagerouter.application.usecase.ListMessageUseCase;
import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageProducerRepository;
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

    private final CreateMessageUseCase createMessageUseCase;
    private final GetMessageUseCase getMessageUseCase;
    private final ListMessageUseCase listMessageUseCase;

    private final MessageProducerRepository messageProducer;

    @Value("${spring.kafka.topic}")
    private String topic;

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestBody
                                                 Message message
    ) {
        log.info(STR."Creating message [\{message}]");
        return ResponseEntity.ok(createMessageUseCase.execute(message.content(), message.timestamp()));
    }

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
    public void sendMessage(@RequestBody String message) {
        log.info(STR."Sending topic [\{topic}] message [\{message}] ");
        messageProducer.produce(topic, message);
    }
}
