package com.eyelis.messagerouter.infrastructure.adapters.persistence.mapper;

import com.eyelis.messagerouter.domain.model.Message;
import com.eyelis.messagerouter.infrastructure.adapters.entity.MessageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "key", target = "key")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timestamp", target = "timestamp")
    Message toDto(MessageEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "key", target = "key")
    @Mapping(source = "content", target = "content")
    @Mapping(source = "timestamp", target = "timestamp")
    MessageEntity toEntity(Message message);

}
