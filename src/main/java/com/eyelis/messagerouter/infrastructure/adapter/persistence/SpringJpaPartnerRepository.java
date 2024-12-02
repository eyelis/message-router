package com.eyelis.messagerouter.infrastructure.adapter.persistence;

import com.eyelis.messagerouter.infrastructure.adapter.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringJpaPartnerRepository extends JpaRepository<PartnerEntity, Long> {
}