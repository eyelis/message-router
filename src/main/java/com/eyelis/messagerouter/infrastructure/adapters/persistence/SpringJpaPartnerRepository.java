package com.eyelis.messagerouter.infrastructure.adapters.persistence;

import com.eyelis.messagerouter.infrastructure.adapters.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaPartnerRepository extends JpaRepository<PartnerEntity, Long> {
}