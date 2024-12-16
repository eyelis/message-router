package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.domain.model.Direction;
import com.eyelis.messagerouter.domain.model.Flow;
import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.infrastructure.adapters.entity.PartnerEntity;
import com.eyelis.messagerouter.infrastructure.adapters.persistence.mapper.PartnerMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        final Partner partner = new Partner(
                null,
                "type",
                "alias",
                Direction.INBOUND,
                "application",
                Flow.ALERTING,
                "description"
        );

        final PartnerEntity entity = PartnerMapper.INSTANCE.toEntity(partner);

        when(repository.save(any(PartnerEntity.class))).thenReturn(entity);

        // when / act
        final Optional<Partner> result = jpaRepository.save(partner);

        // then / assert
        assertThat(result).isPresent();
        assertThat(result.get())
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
        final List<PartnerEntity> entities = List.of(
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
        final List<Partner> result = jpaRepository.findAll();

        // then / assert
        assertThat(result).hasSize(2);

        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(entities
                        .stream()
                        .map(PartnerMapper.INSTANCE::toDto)
                        .collect(Collectors.toList())
                );

        verify(repository).findAll();
    }

}
