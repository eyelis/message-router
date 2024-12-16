package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.domain.model.Direction;
import com.eyelis.messagerouter.domain.model.Flow;
import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.infrastructure.adapters.entity.PartnerEntity;
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
public class JpaPartnerRepositoryTest {

    @Mock
    SpringJpaPartnerRepository repository;

    @InjectMocks
    JpaPartnerRepository jpaRepository;

    @Captor
    ArgumentCaptor<PartnerEntity> entityCaptor;

    @Test
    void shouldCreatePartner() {

        //given / arrange
        Partner partner = new Partner(
                null,
                "type",
                "alias",
                Direction.INBOUND,
                "application",
                Flow.ALERTING,
                "description"
        );

        PartnerEntity entity = new PartnerEntity(
                partner.id(),
                partner.type(),
                partner.alias(),
                partner.direction(),
                partner.application(),
                partner.flow(),
                partner.description()
        );
        when(repository.save(any(PartnerEntity.class))).thenReturn(entity);

        // when / act
        Partner result = jpaRepository.save(partner);

        // then / assert
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(partner);

        verify(repository).save(entityCaptor.capture());
        assertThat(entity)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(entityCaptor.getValue());
    }

    @Test
    void shouldRetrieveAllPartners() {

        //given / arrange
        final LocalDateTime date = LocalDateTime.now();
        List<PartnerEntity> entities = List.of(
                new PartnerEntity(
                        null,
                        "type1",
                        "alias1",
                        Direction.INBOUND,
                        "application1",
                        Flow.ALERTING,
                        "description1"
                ),
                new PartnerEntity(
                        null,
                        "type2",
                        "alias2",
                        Direction.OUTBOUND,
                        "application2",
                        Flow.NOTIFICATION,
                        "description2"
                )
        );
        when(repository.findAll()).thenReturn(entities);

        // when / act
        List<Partner> result = jpaRepository.findAll();

        // then / assert
        assertThat(result).hasSize(2);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(entities
                        .stream()
                        .map(entity -> new Partner(
                                entity.id(),
                                entity.type(),
                                entity.alias(),
                                entity.direction(),
                                entity.application(),
                                entity.flow(),
                                entity.description()
                        ))
                        .collect(Collectors.toList())
                );

        verify(repository).findAll();
    }

}
