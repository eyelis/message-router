package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.PartnerRepository;
import com.eyelis.messagerouter.domain.model.Partner;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreatePartnerUseCase {

    private final PartnerRepository partnerRepository;

    public Optional<Partner> execute(final Partner partner) {
        return partnerRepository.save(partner);
    }
}
