package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatePartnerUseCase {

     private final PartnerRepository partnerRepository;

    public Partner execute(Partner partner) {
        return partnerRepository.save(partner);
    }
}
