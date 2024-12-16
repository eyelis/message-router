package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.PartnerRepository;
import com.eyelis.messagerouter.domain.model.Partner;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetPartnerUseCase {

    private final PartnerRepository partnerRepository;

    public Optional<Partner> execute(Long id) {
        return partnerRepository.findById(id);
    }
}
