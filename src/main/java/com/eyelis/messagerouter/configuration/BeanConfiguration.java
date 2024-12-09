package com.eyelis.messagerouter.configuration;

import com.eyelis.messagerouter.application.usecase.*;
import com.eyelis.messagerouter.domain.repository.MessageRepository;
import com.eyelis.messagerouter.domain.repository.PartnerRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public GetMessageUseCase getMessageUseCase(MessageRepository repository) {
        return new GetMessageUseCase(repository);
    }

    @Bean
    public ListMessageUseCase listMessageUseCase(MessageRepository repository) {
        return new ListMessageUseCase(repository);
    }

    @Bean
    public DeleteMessageUseCase deleteMessageUseCase(MessageRepository repository) {
        return new DeleteMessageUseCase(repository);
    }


    @Bean
    public CreatePartnerUseCase createPartnerUseCase(PartnerRepository repository) {
        return new CreatePartnerUseCase(repository);
    }

    @Bean
    public GetPartnerUseCase getPartnerUseCase(PartnerRepository repository) {
        return new GetPartnerUseCase(repository);
    }

    @Bean
    public ListPartnerUseCase listPartnerUseCase(PartnerRepository repository) {
        return new ListPartnerUseCase(repository);
    }

    @Bean
    public DeletePartnerUseCase deletePartnerUseCase(PartnerRepository repository) {
        return new DeletePartnerUseCase(repository);
    }
}
