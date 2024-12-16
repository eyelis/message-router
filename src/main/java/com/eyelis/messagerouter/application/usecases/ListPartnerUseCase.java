package com.eyelis.messagerouter.application.usecases;

import com.eyelis.messagerouter.application.ports.out.PartnerRepository;
import com.eyelis.messagerouter.domain.model.Partner;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListPartnerUseCase {

    private final PartnerRepository partnerRepository;

    public List<Partner> execute() {
        return partnerRepository.findAll();
    }
}
