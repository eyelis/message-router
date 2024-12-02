package com.eyelis.messagerouter.application.usecase;

import com.eyelis.messagerouter.domain.model.Partner;
import com.eyelis.messagerouter.domain.repository.PartnerRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ListPartnerUseCase {

    private final PartnerRepository partnerRepository;

    public List<Partner> execute() {
        return partnerRepository.findAll();
    }
}
