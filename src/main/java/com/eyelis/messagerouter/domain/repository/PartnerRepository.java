package com.eyelis.messagerouter.domain.repository;

import com.eyelis.messagerouter.domain.model.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository {
    Partner save(Partner partner);

    Optional<Partner> findById(Long id);

    List<Partner> findAll();

    void deleteById(Long id);
}
