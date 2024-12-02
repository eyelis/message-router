package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class GetPartnerUseCase {

    private final PartnerRepository partnerRepository;

    public Optional<Partner> execute(Long id) {
        return partnerRepository.findById(id);
    }
}
