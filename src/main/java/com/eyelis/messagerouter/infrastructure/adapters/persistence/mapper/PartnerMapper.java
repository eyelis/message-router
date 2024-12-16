package com.eyelis.messagerouter.infrastructure.adapters.persistence.mapper;

import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.infrastructure.adapters.entity.PartnerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PartnerMapper {
    PartnerMapper INSTANCE = Mappers.getMapper(PartnerMapper.class);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "alias", target = "alias")
    @Mapping(source = "direction", target = "direction")
    @Mapping(source = "application", target = "application")
    @Mapping(source = "flow", target = "flow")
    @Mapping(source = "description", target = "description")
    Partner toDto(PartnerEntity entity);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "type", target = "type")
    @Mapping(source = "alias", target = "alias")
    @Mapping(source = "direction", target = "direction")
    @Mapping(source = "application", target = "application")
    @Mapping(source = "flow", target = "flow")
    @Mapping(source = "description", target = "description")
    PartnerEntity toEntity(Partner partner);
}
