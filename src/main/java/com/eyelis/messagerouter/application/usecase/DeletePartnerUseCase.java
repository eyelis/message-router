package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeletePartnerUseCase {

    private final PartnerRepository partnerRepository;

    public void execute(Long id) {
        partnerRepository.deleteById(id);
    }
}
