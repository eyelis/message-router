package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.PartnerRepository;
import com.eyelis.messagerouter.domain.model.Direction;
import com.eyelis.messagerouter.domain.model.Flow;
import com.eyelis.messagerouter.domain.model.Partner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreatePartnerUseCaseTest {

    @Mock
    PartnerRepository repository;

    @InjectMocks
    CreatePartnerUseCase useCase;

    @Captor
    ArgumentCaptor<Partner> partnerCaptor;

    @Test
    void shouldCreateMessage() {
        // given / arrange
        final Partner partner = new Partner(
                1L,
                "type",
                "alias",
                Direction.INBOUND,
                "application",
                Flow.ALERTING,
                "description"
        );

        when(repository.save(any(Partner.class))).thenReturn(partner);

        // when / act
        final Partner result = useCase.execute(partner);

        // then / assert
        assertThat(result).isNotNull();
        assertThat(result)
                .usingRecursiveComparison()
                .isEqualTo(partner);

        verify(repository).save(partnerCaptor.capture());

        assertThat(result)
                .usingRecursiveComparison()
                .ignoringFields("id")
                .isEqualTo(partnerCaptor.getValue());
    }
}
