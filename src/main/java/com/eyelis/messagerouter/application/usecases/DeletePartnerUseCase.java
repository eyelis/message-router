package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.PartnerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletePartnerUseCase {

    private final PartnerRepository partnerRepository;

    public void execute(final Long id) {
        partnerRepository.deleteById(id);
    }
}
