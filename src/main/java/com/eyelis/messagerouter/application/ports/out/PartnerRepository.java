package com.eyelis.messagerouter.application.ports.out;

import com.eyelis.messagerouter.domain.model.Partner;

import java.util.List;
import java.util.Optional;

public interface PartnerRepository {
    Optional<Partner> save(Partner partner);

    Optional<Partner> findById(Long id);

    List<Partner> findAll();

    void deleteById(Long id);
}
