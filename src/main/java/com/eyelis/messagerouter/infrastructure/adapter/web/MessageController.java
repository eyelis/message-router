package com.eyelis.messagerouter.infrastructure.adapter.web;

import com.eyelis.messagerouter.application.usecase.CreateMessageUseCase;
import com.eyelis.messagerouter.application.usecase.GetMessageUseCase;
import com.eyelis.messagerouter.application.usecase.ListMessageUseCase;
import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.domain.repository.MessageProducerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final CreateMessageUseCase createMessageUseCase;
    private final GetMessageUseCase getMessageUseCase;
    private final ListMessageUseCase listMessageUseCase;

    private final MessageProducerRepository messageProducer;

    @Value("${spring.kafka.topic}")
    private String topic;

    @PostMapping
    public ResponseEntity<Message> createMessage(@RequestParam
                                 String content,
                                 @RequestParam
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                 LocalDateTime timestamp
    ) {
        return ResponseEntity.ok(createMessageUseCase.execute(content, timestamp));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Message> getMessage(@PathVariable Long id) {
        Optional<Message> message = getMessageUseCase.execute(id);
        return message.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Message>> listMessages() {
        return ResponseEntity.ok(listMessageUseCase.execute());
    }

    @PostMapping("send")
    public void sendMessage(@RequestParam String message) {
        messageProducer.produce(topic, message);
    }
}
